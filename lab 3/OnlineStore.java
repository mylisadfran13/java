import java.util.*;

class Product {
    private String name;
    private double price;
    
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    public String getName() { return name; }
    public double getPrice() { return price; }
    
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    
    @Override
    public String toString() {
        return name + " (" + price + " руб.)";
    }
}

class Order {
    private String orderNumber;
    private Date orderDate;
    private List<Product> products;
    private String status;
    
    public Order(String orderNumber, Date orderDate, List<Product> products, String status) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.products = products;
        this.status = status;
    }
    
    public String getOrderNumber() { return orderNumber; }
    public Date getOrderDate() { return orderDate; }
    public List<Product> getProducts() { return products; }
    public String getStatus() { return status; }
    
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public void setProducts(List<Product> products) { this.products = products; }
    public void setStatus(String status) { this.status = status; }
    

    public double getTotalPrice() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
    
    @Override
    public String toString() {
        return "заказ №" + orderNumber + 
               "\nдата: " + orderDate + 
               "\nтовары: " + products + 
               "\nстатус: " + status + 
               "\nобщая стоимость: " + getTotalPrice() + " руб.";
    }
}


class OrderHashTable {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    
    private LinkedList<Order>[] table;
    private int size;
    
    @SuppressWarnings("unchecked")
    public OrderHashTable() {
        table = new LinkedList[DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            table[i] = new LinkedList<>();
        }
        size = 0;
    }
    
    private int hash(String orderNumber) {
        return Math.abs(orderNumber.hashCode()) % table.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        LinkedList<Order>[] oldTable = table;
        table = new LinkedList[table.length * 2];
        
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<>();
        }
        
        size = 0;

        for (LinkedList<Order> bucket : oldTable) {
            for (Order order : bucket) {
                insert(order);
            }
        }
    }
    
    public void insert(Order order) {
        if ((double) size / table.length > LOAD_FACTOR) {
            resize();
        }
        
        int index = hash(order.getOrderNumber());
        LinkedList<Order> bucket = table[index];
        
        //проверка
        for (Order existingOrder : bucket) {
            if (existingOrder.getOrderNumber().equals(order.getOrderNumber())) {
                existingOrder.setOrderDate(order.getOrderDate());
                existingOrder.setProducts(order.getProducts());
                existingOrder.setStatus(order.getStatus());
                return;
            }
        }
        
        bucket.add(order);
        size++;
    }
    
    public Order search(String orderNumber) {
        int index = hash(orderNumber);
        LinkedList<Order> bucket = table[index];
        
        for (Order order : bucket) {
            if (order.getOrderNumber().equals(orderNumber)) {
                return order;
            }
        }
        
        return null; 
    }
    
    public boolean delete(String orderNumber) {
        int index = hash(orderNumber);
        LinkedList<Order> bucket = table[index];
        
        Iterator<Order> iterator = bucket.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (order.getOrderNumber().equals(orderNumber)) {
                iterator.remove();
                size--;
                return true;
            }
        }
        
        return false; 
    }

    public boolean updateStatus(String orderNumber, String newStatus) {
        Order order = search(orderNumber);
        if (order != null) {
            order.setStatus(newStatus);
            return true;
        }
        return false;
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    

    public void displayAllOrders() {
        System.out.println("все заказы в системе:");
        for (LinkedList<Order> bucket : table) {
            for (Order order : bucket) {
                System.out.println(order);
                System.out.println("     ");
            }
        }
    }
}

public class OnlineStore {
    public static void main(String[] args) {
        OrderHashTable orderTable = new OrderHashTable();

        List<Product> products1 = Arrays.asList(
            new Product("ноут", 50000),
            new Product("мышка", 1500)
        );
        
        List<Product> products2 = Arrays.asList(
            new Product("телеф", 30000),
            new Product("чехол", 1000)
        );
        
        List<Product> products3 = Arrays.asList(
            new Product("наушники", 5000),
            new Product("планшет", 25000)
        );
        

        Order order1 = new Order("ORD001", new Date(), products1, "обрабатывается");
        Order order2 = new Order("ORD002", new Date(), products2, "доставляется");
        Order order3 = new Order("ORD003", new Date(), products3, "выполнен");
        

        orderTable.insert(order1);
        orderTable.insert(order2);
        orderTable.insert(order3);
        
        System.out.println("добавлено заказов: " + orderTable.size());

        System.out.println("\nпоиск заказа ORD002:");
        Order foundOrder = orderTable.search("ORD002");
        if (foundOrder != null) {
            System.out.println(foundOrder);
        } else {
            System.out.println("заказ не найден");
        }
        
        System.out.println("\nизменение статуса заказа ORD001 на 'доставлен':");
        if (orderTable.updateStatus("ORD001", "доставлен")) {
            System.out.println("Статус успешно изменен");
            System.out.println(orderTable.search("ORD001"));
        } else {
            System.out.println("заказ не найден");
        }

        System.out.println("\nудаление заказа ORD003:");
        if (orderTable.delete("ORD003")) {
            System.out.println("заказ успешно удален");
        } else {
            System.out.println("заказ не найден");
        }
        
        System.out.println("\nосталось заказов: " + orderTable.size());
        
        System.out.println("\nпоиск удаленного заказа ORD003:");
        Order deletedOrder = orderTable.search("ORD003");
        if (deletedOrder != null) {
            System.out.println(deletedOrder);
        } else {
            System.out.println("заказ не найден");
        }

        System.out.println("\n" + "=".repeat(50));
        orderTable.displayAllOrders();
    }
}