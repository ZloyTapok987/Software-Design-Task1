import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LRUCache<K, V> {
    protected final int Capacity;
    protected Node Head = null, Tail = null;
    private final Map<K, Node> Map = new HashMap<>();

    public LRUCache(int capacity) {
        this.Capacity = capacity;
    }


    protected class Node {
        @NotNull
        final K key;
        @NotNull
        final V value;
        Node prev, next;

        Node(@NotNull K key, @NotNull V value) {
            this.key = key;
            this.value = value;
        }
    }

    protected int size() {
        int res = 0;
        Node cur = Head;
        while (cur != null) {
            cur = cur.next;
            res++;
        }
        return res;
    }


    public void put(@NotNull K key, @NotNull V value) {
        removeNode(key);
        addNode(key, value);
        assert (Head != null && Tail != null) : "Element not inserted";
        assert Head.value == value : "Element is not at the top";
        assert size() <= Capacity : "Cache is full";
    }

    public Optional<V> get(@NotNull K key) {
        int prevSize = size();
        if (!Map.containsKey(key)) {
            return Optional.empty();
        }
        V r = Map.get(key).value;
        removeNode(key);
        addNode(key, r);
        Optional<V> res = Optional.of(r);
        assert prevSize == size() : "Size changed";
        return res;
    }

    private void removeNode(@NotNull K key) {
        int prevSize = size();
        if (!Map.containsKey(key)) {
            return ;
        }
        Node current = Map.get(key);
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            Tail = current.prev;
        }
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            Head = current.next;
        }
        Map.remove(key);
        assert prevSize - 1 == size() : "Deleted more than one element";
    }

    private void addNode(@NotNull K key, @NotNull V value) {
        int prevSize = size();
        Node newNode = new Node(key, value);
        newNode.next = Head;
        if (Head != null) {
            Head.prev = newNode;
        }
        Head = newNode;
        if (Tail == null) {
            Tail = newNode;
        }
        Map.put(key, newNode);

        assert prevSize + 1 == size() : "Added more than one element";

        if (Map.size() > Capacity) {
            removeNode(Tail.key);
        }
    }
}