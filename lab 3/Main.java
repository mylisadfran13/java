public class Main {
    public static void main(String[] args) {        
        HashTable<String, Integer> table = new HashTable<>();
        
        System.out.println("основные операции");
        
        System.out.println("1. пустая таблица:");
        System.out.println("   размер: " + table.size());
        System.out.println("   пустая: " + table.isEmpty());
        table.printTable();
        
        System.out.println("\n2. +элементы:");
        table.put("apple", 10);
        table.put("banana", 20);
        table.put("orange", 30);
        table.put("grape", 40);
        
        System.out.println("   размер после добавления: " + table.size());
        System.out.println("   пустая: " + table.isEmpty());
        table.printTable();
        
        System.out.println("\n3. получаем значения:");
        System.out.println("   apple = " + table.get("apple"));
        System.out.println("   banana = " + table.get("banana"));
        System.out.println("   orange = " + table.get("orange"));
        System.out.println("   grape = " + table.get("grape"));
        System.out.println("   несуществующий ключ = " + table.get("mango"));
        

        System.out.println("\n4. проверка наличия ключей:");
        System.out.println("   есть ли 'apple': " + table.containsKey("apple"));
        System.out.println("   есть ли 'mango': " + table.containsKey("mango"));
        System.out.println("   есть ли 'orange': " + table.containsKey("orange"));
        

        System.out.println("\n5. обнова значения:");
        System.out.println("   apple до обновления = " + table.get("apple"));
        table.put("apple", 99);
        System.out.println("   apple после обновления = " + table.get("apple"));
        System.out.println("   Размер остался прежним: " + table.size());
        
        System.out.println("\n6. удаление:");
        Integer removed1 = table.remove("banana");
        System.out.println("   удален banana = " + removed1);
        
        Integer removed2 = table.remove("mango");
        System.out.println("   попытка удалить несуществующий mango = " + removed2);
        
        System.out.println("   размер после удаления: " + table.size());
        table.printTable();
        

        System.out.println("\n новая таблица!!!!!!!!!!!");
        HashTable<Integer, String> numberTable = new HashTable<>(5);
        
        numberTable.put(1, "один");
        numberTable.put(2, "два");
        numberTable.put(3, "три");
        
        System.out.println("1 = " + numberTable.get(1));
        System.out.println("2 = " + numberTable.get(2));
        numberTable.printTable();
        
    }
}