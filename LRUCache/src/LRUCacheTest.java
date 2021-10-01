import org.junit.*;

import java.util.Optional;

public class LRUCacheTest {
    @Test
    public void testEmptyCache() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        Assert.assertFalse(cache.get(0).isPresent());
    }

    @Test
    public void testGet() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        cache.put(0, 0);
        Assert.assertEquals(Optional.of(0), cache.get(0));
    }

    @Test
    public void testModify() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        cache.put(0, 0);
        cache.put(0, 1);
        Assert.assertEquals(Optional.of(1), cache.get(0));
    }

    @Test
    public void testRemove() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(2);

        cache.put(0, 0);
        cache.put(1, 1);
        cache.put(2, 2);
        Assert.assertFalse(cache.get(0).isPresent());
    }

    @Test
    public void testPushByPut() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(2);

        cache.put(0, 0);
        cache.put(1, 1);
        cache.put(0, 0);
        cache.put(2, 2);
        Assert.assertFalse(cache.get(1).isPresent());
    }

    @Test
    public void testPushByGet() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(2);

        cache.put(0, 0);
        cache.put(1, 1);
        cache.get(0);
        cache.put(2, 2);
        Assert.assertFalse(cache.get(1).isPresent());
    }

}
