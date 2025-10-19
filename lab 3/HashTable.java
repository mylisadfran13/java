import java.util.LinkedList;

public class HashTable<K, V> {
    private LinkedList<Entry<K, V>>[] table;
    private int size;
    private int capacity;
    
    private static class Entry<K, V> {
        private K key;
        private V value;
        
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public K getKey() {
            return key;
        }
        
        public V getValue() {
            return value;
        }
        
        public void setValue(V value) {
            this.value = value;
        }
    }

    public HashTable() {
        this(16);  
    }
    
    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        this.capacity = capacity;
        this.table = new LinkedList[capacity];
        this.size = 0;
    }
    

    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }
    
    //put
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<Entry<K, V>>();
        }
        
        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }

        table[index].add(new Entry<K, V>(key, value));
        size++;
    }
    
    //get
    public V get(K key) {
        if (key == null) {
            return null;
        }
        
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }
        
        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        
        return null;
    }
    
    //remove
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }
        
        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                V value = entry.getValue();
                table[index].remove(entry);
                size--;

                if (table[index].isEmpty()) {
                    table[index] = null;
                }
                
                return value;
            }
        }
        
        return null;
    }
    
    //size 
    public int size() {
        return size;
    }
    
    //isEmpty
    public boolean isEmpty() {
        return size == 0;
    }
    
    //containsKey
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    
    public void printTable() {
        System.out.println("HashTable (size: " + size + ", capacity: " + capacity + "):");
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                System.out.print("Bucket " + i + ": ");
                for (Entry<K, V> entry : table[i]) {
                    System.out.print("[" + entry.getKey() + "=" + entry.getValue() + "] ");
                }
                System.out.println();
            }
        }
    }
}