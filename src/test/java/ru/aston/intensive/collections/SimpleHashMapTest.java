package ru.aston.intensive.collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SimpleHashMapTest {

    @Test
    public void testPutAndGetValue() {
        SimpleHashMap<String, Integer> map = fillMap();
        int size = map.size();

        map.put("Four", 4);

        assertEquals(size + 1, map.size());
        assertEquals(4, map.get("Four"));
    }

    @Test
    public void testPutAndOverwriteValue() {
        SimpleHashMap<String, Integer> map = fillMap();
        int size = map.size();

        map.put("One", 111);

        assertEquals(size, map.size());
        assertEquals(111, map.get("One"));
    }

    @Test
    public void testPutNullKey() {
        SimpleHashMap<String, Integer> map = fillMap();
        int size = map.size();

        map.put(null, null);

        assertEquals(size + 1, map.size());
        assertNull(map.get(null));
    }

    @Test
    public void testIfAbsent() {
        SimpleHashMap<String, Integer> map = fillMap();

        assertNull(map.get("AAA"));
    }

    @Test
    public void testRemove() {
        SimpleHashMap<String, Integer> map = fillMap();

        int size = map.size();

        int oldValue = map.remove("a");

        assertEquals(size - 1, map.size());
        assertEquals(80, oldValue);
        assertNull(map.get("a"));
    }

    @Test
    public void testResizeDefaultCapacity() {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();
        int elementCount = 30;

        for (int i = 0; i < elementCount; i++) {
            map.put("value" + i, i);
        }

        assertEquals(elementCount, map.size());
        assertEquals(5, map.get("value5"));
        assertEquals(19, map.get("value19"));
    }

    @Test
    public void testResizeInitialCapacity() {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>(4);
        int elementCount = 30;

        for (int i = 0; i < elementCount; i++) {
            map.put("value" + i, i);
        }
        assertEquals(elementCount, map.size());
        assertEquals(19, map.get("value19"));
    }

    private SimpleHashMap<String, Integer> fillMap() {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();
        map.put("One", 10);
        map.put("Two", 20);
        map.put("Three", 30);
        map.put("Aa", 40);
        map.put("BB", 50);
        map.put("A", 60);
        map.put("Q", 70);
        map.put("a", 80);
        map.put("Hello", 90);
        map.put("World", 100);

        return map;
    }

}