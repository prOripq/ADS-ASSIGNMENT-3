import java.util.ArrayList;
import java.util.List;

public class MyHashTable<K, V>  {
    private static final int DEFAULT_CAPACITY = 11;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    private class HashNode<K, V> {
        private K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }

    private HashNode<K, V>[] table;
    private int size;

    public MyHashTable() {
        this(DEFAULT_CAPACITY);
    }

    public MyHashTable(int initialCapacity) {
        table = (HashNode<K, V>[]) new HashNode[initialCapacity];
        size = 0;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % table.length;
    }

    public void put(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null.");

        int index = hash(key);
        HashNode<K, V> node = table[index];

        while (node != null) {
            if (node.key.equals(key)) {
                node.value = value; // Update value if key already exists
                return;
            }
            node = node.next;
        }

        HashNode<K, V> newNode = new HashNode<>(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;

        // Check if rehashing is needed
        if ((double) size / table.length >= LOAD_FACTOR_THRESHOLD)
            resize(2 * table.length);
    }

    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null.");

        int index = hash(key);
        HashNode<K, V> node = table[index];

        while (node != null) {
            if (node.key.equals(key))
                return node.value;
            node = node.next;
        }

        return null; // Key not found
    }

    public V remove(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null.");

        int index = hash(key);
        HashNode<K, V> prev = null;
        HashNode<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null)
                    table[index] = current.next;
                else
                    prev.next = current.next;
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }

        return null; // Key not found
    }

    public boolean containsValue(V value) {
        for (HashNode<K, V> node : table) {
            while (node != null) {
                if (node.value.equals(value))
                    return true;
                node = node.next;
            }
        }
        return false;
    }

    public K getKey(V value) {
        for (HashNode<K, V> node : table) {
            while (node != null) {
                if (node.value.equals(value))
                    return node.key;
                node = node.next;
            }
        }
        return null;
    }

    private void resize(int newCapacity) {
        HashNode<K, V>[] newTable = (HashNode<K, V>[]) new HashNode[newCapacity];

        for (HashNode<K, V> currentNode : table) {
            while (currentNode != null) {
                HashNode<K, V> nextNode = currentNode.next;
                int newIndex = (currentNode.key.hashCode() & 0x7fffffff) % newCapacity;

                if (newTable[newIndex] == null) {
                    newTable[newIndex] = currentNode;
                    currentNode.next = null;
                } else {
                    currentNode.next = newTable[newIndex];
                    newTable[newIndex] = currentNode;
                }

                currentNode = nextNode;
            }
        }


        table = newTable;
    }


    @Override
    public String toString() {
        List<String> pairs = new ArrayList<>();
        for (HashNode<K, V> node : table) {
            while (node != null) {
                pairs.add(node.toString());
                node = node.next;
            }
        }
        return "{" + String.join(", ", pairs) + "}";
    }
}
