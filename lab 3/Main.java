public class Main {
    public static void main(String[] args) {
        HashTable<String, Integer> hashTable = new HashTable<>();
        
        hashTable.put("яблочко", 10);
        hashTable.put("бананчик", 20);
        hashTable.put("апельсин", 30);
        
        System.out.println("размер таб: " + hashTable.size()); 
        System.out.println("является ли пустой: " + hashTable.isEmpty()); 
        
        System.out.println("значение для 'яблочко': " + hashTable.get("яблочко")); 
        System.out.println("Значение для 'бананчик': " + hashTable.get("бананчик")); 
        
        hashTable.put("яблочко", 15);
        System.out.println("обновленное значение для 'яблочко': " + hashTable.get("яблочко")); 
        
        Integer removed = hashTable.remove("бананчик");
        System.out.println("удаленное значение: " + removed); 
        System.out.println("размер после удаления: " + hashTable.size());
        
        System.out.println("значение для 'виноградик': " + hashTable.get("виноградик")); 
    }
}