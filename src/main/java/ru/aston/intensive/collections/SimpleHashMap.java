package ru.aston.intensive.collections;

public class SimpleHashMap<K, V> {

    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K, V>[] buckets;

    private int size;

    private int threshold;

    private int capacity;

    @SuppressWarnings("unchecked")
    public SimpleHashMap(int capacity) {
        this.capacity = capacity;
        this.threshold = (int) (capacity * DEFAULT_LOAD_FACTOR);
        buckets = (Node<K, V>[]) new Node[capacity];
    }

    public SimpleHashMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V get(Object key) {
        Node<K, V> firstNode;
        Node<K, V> nextNode;
        int hashKey = hash(key);
        if (buckets == null || (firstNode = buckets[getBucketIndex(key)]) == null || key == null) {
            return null;
        }

        if (hash(firstNode.key) == hashKey && firstNode.key.equals(key)) {
            return firstNode.value;
        }

        if ((nextNode = firstNode.next) != null) {
            do {
                if (hash(nextNode.key) == hashKey && nextNode.key.equals(key)) {
                    return nextNode.value;
                }
            }
            while ((nextNode = nextNode.next) != null);
        }
        return null;
    }

    public V put(K key, V value) {
        int bucketIndex = getBucketIndex(key);

        Node<K, V> node = buckets[bucketIndex];
        Node<K, V> newNode = new Node<>(key, value, null);

        if (node == null) {
            this.buckets[bucketIndex] = newNode;
            ++size;
            return null;
        }

        Node<K, V> currentNode = null;
        while (node != null) {
            if (node.key.equals(key)) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            currentNode = node;
            node = node.next;
        }

        currentNode.next = newNode;

        if (++size > threshold) {
            resize();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldBuckets = buckets;
        int oldCapacity = capacity;
        int newCapacity;
        int newThreshold;

        if (oldCapacity >= MAXIMUM_CAPACITY) {
            throw new RuntimeException("Exceeded capacity limit");
        }

        if ((newCapacity = oldCapacity << 1) < MAXIMUM_CAPACITY) {
            newThreshold = (int) (newCapacity * DEFAULT_LOAD_FACTOR);
        } else {
            newCapacity = MAXIMUM_CAPACITY;
            newThreshold = newCapacity - 1;
        }

        Node<K, V>[] newBuckets = (Node<K, V>[]) new Node[newCapacity];
        this.threshold = newThreshold;
        this.capacity = newCapacity;

        for (Node<K, V> node : oldBuckets) {
            while (node != null) {
                Node<K, V> nextNode = node.next;
                node.next = null;

                int newIndex = getBucketIndex(node.key);

                if (newBuckets[newIndex] == null) {
                    newBuckets[newIndex] = node;
                } else {
                    Node<K, V> currentNode = newBuckets[newIndex];
                    while (currentNode.next != null) {
                        currentNode = currentNode.next;
                    }
                    currentNode.next = node;
                }
                node = nextNode;
            }
        }
        buckets = newBuckets;
    }

    public V remove(Object key) {
        int keyIndex = getBucketIndex(key);
        Node<K, V> node;
        if ((node = buckets[keyIndex]) != null) {
            if (node.key.equals(key)) {
                if (node.next != null) {
                    buckets[keyIndex] = node.next;
                } else {
                    buckets[keyIndex] = null;
                }
                --size;
                return node.value;
            } else {
                while (node.next != null) {
                    Node<K, V> nextNode = node.next;
                    if (nextNode.key.equals(key)) {
                        node.next = nextNode.next;
                        --size;
                        return nextNode.value;
                    }
                    node = node.next;
                }
            }
        }
        return null;
    }

    private int getBucketIndex(Object key) {
        return hash(key) & (capacity - 1);
    }

    private int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    static class Node<K, V> {

        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

    }

}
