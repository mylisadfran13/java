import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Warehouse {
    private final List<Integer> goods;
    private int currentIndex; 
    private int currentWeight;
    private int iterationCount; 

    private final Lock lock;
    private final Condition notEnoughWeight; 
    private final Condition deliveryInProgress; 
    
    private boolean isDelivering; 
    
    public Warehouse(List<Integer> goods) {
        this.goods = new ArrayList<>(goods);
        this.currentIndex = 0;
        this.currentWeight = 0;
        this.iterationCount = 0;
        this.isDelivering = false;
        
        this.lock = new ReentrantLock();
        this.notEnoughWeight = lock.newCondition();
        this.deliveryInProgress = lock.newCondition();
    }
    public Integer takeGood(int loaderId) throws InterruptedException {
        lock.lock();
        try {
            while (isDelivering) {
                System.out.printf("[грузчик %d] ждет окончания доставки...%n", loaderId);
                deliveryInProgress.await();
            }

            if (currentIndex >= goods.size()) {
                if (currentWeight > 0) {
                    startDelivery(loaderId);
                }
                return null;
            }

            int weight = goods.get(currentIndex);
            currentWeight += weight;
            currentIndex++;
            
            System.out.printf("[грузчик %d] взял товар %d кг. партия: %d/%d кг. осталось: %d шт.%n",
                    loaderId, weight, currentWeight, 150, goods.size() - currentIndex);
            
            if (currentWeight >= 150) {
                startDelivery(loaderId);
            }
            
            return weight;
        } finally {
            lock.unlock();
        }
    }
    
    private void startDelivery(int loaderId) {
        iterationCount++;
        System.out.printf("\nгрузчик %d сделал отправку партии #%d (%d кг)%n",
                loaderId, iterationCount, currentWeight);
        
        isDelivering = true;
        

        new Thread(() -> {
            try {
                deliverBatch(iterationCount, currentWeight);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    private void deliverBatch(int batchNumber, int weight) throws InterruptedException {
        System.out.printf("[доставка #%d] началась погрузка...%n", batchNumber);
        Thread.sleep(1000); 
        
        System.out.printf("[доставка #%d] В пути...%n", batchNumber);
        Thread.sleep(1500); 
        
        System.out.printf("[доставка #%d] Разгрузка на другом складе...%n", batchNumber);
        Thread.sleep(1000);
        
        System.out.printf("[доставка #%d] партия %d кг доставлена наконец-то%n\n", batchNumber, weight);
        
        lock.lock();
        try {
            currentWeight = 0;
            isDelivering = false;
            deliveryInProgress.signalAll();
            notEnoughWeight.signalAll(); 
        } finally {
            lock.unlock();
        }
    }

    public boolean hasGoods() {
        lock.lock();
        try {
            return currentIndex < goods.size() || currentWeight > 0;
        } finally {
            lock.unlock();
        }
    }
}

class Loader implements Runnable {
    private final int id;
    private final Warehouse warehouse;
    
    public Loader(int id, Warehouse warehouse) {
        this.id = id;
        this.warehouse = warehouse;
    }
    
    @Override
    public void run() {
        try {
            System.out.printf("[грузчик %d] Начал работу%n", id);
            
            while (warehouse.hasGoods()) {
                Integer weight = warehouse.takeGood(id);
                if (weight == null) {
                    break;
                }
                Thread.sleep(200 + new Random().nextInt(300));
            }
            
            System.out.printf("[грузчик %d] завершил работу%n", id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class WarehouseSimulation {
    public static void main(String[] args) {
        Random random = new Random();
        List<Integer> goods = new ArrayList<>();
        
        for (int i = 0; i < 25; i++) {
            goods.add(20 + random.nextInt(51));
        }
        
        System.out.println("работа склада");
        System.out.println("товары: " + goods);
        System.out.println("всего вес: " + goods.stream().mapToInt(Integer::intValue).sum() + " кг");
        System.out.println("количество грузчиков: 3");
        System.out.println("лимит за итерацию: 150 кг");
        
        Warehouse warehouse = new Warehouse(goods);
        
        Thread[] loaders = new Thread[3];
        for (int i = 0; i < 3; i++) {
            loaders[i] = new Thread(new Loader(i + 1, warehouse));
        }

        for (Thread loader : loaders) {
            loader.start();
        }

        for (Thread loader : loaders) {
            try {
                loader.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("все товары перенесены ура");
    }
}