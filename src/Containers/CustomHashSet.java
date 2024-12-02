package Containers;

import java.util.Iterator;
import java.util.LinkedList;

public class CustomHashSet<T> implements Iterable<T> {
    private LinkedList<T>[] buckets;
    private int size;
    private static final int INITIAL_CAPACITY = 16;

    public CustomHashSet() {
        buckets = new LinkedList[INITIAL_CAPACITY];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>(); // Initialize each bucket
        }
        size = 0;
    }

    public void put(T element) {
        int index = getBucketIndex(element);
        if (!buckets[index].contains(element)) {
            buckets[index].add(element);
            size++;
            if ((double) size / buckets.length > 0.75) { // Resize if load factor exceeds 0.75
                resize();
            }
        }
    }

    public boolean contains(T element) {
        int index = getBucketIndex(element);
        return buckets[index] != null && buckets[index].contains(element);
    }

    public void delete(T element) {
        int index = getBucketIndex(element);
        if (buckets[index] != null && buckets[index].remove(element)) {
            size--;
        }
    }

    public int size() {
        return size;
    }

    private int getBucketIndex(T element) {
        return (element == null ? 0 : element.hashCode() & 0x7FFFFFFF) % buckets.length;
    }

    private void resize() {
        LinkedList<T>[] oldBuckets = buckets;
        buckets = new LinkedList[oldBuckets.length * 2];
        size = 0;

        for (LinkedList<T> bucket : oldBuckets) {
            if (bucket != null) {
                for (T element : bucket) {
                    put(element); // Rehash
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (LinkedList<T> bucket : buckets) {
            if (bucket != null) {
                for (T element : bucket) {
                    sb.append(element).append(", ");
                }
            }
        }
        if (sb.length() > 1) sb.setLength(sb.length() - 2); // Remove trailing ", "
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomHashSetIterator();
    }

    private class CustomHashSetIterator implements Iterator<T> {
        private int bucketIndex = 0;
        private Iterator<T> bucketIterator = null;

        public CustomHashSetIterator() {
            advanceToNextNonEmptyBucket();
        }

        @Override
        public boolean hasNext() {
            return bucketIterator != null && bucketIterator.hasNext();
        }

        @Override
        public T next() {
            T nextElement = bucketIterator.next();
            if (!bucketIterator.hasNext()) {
                bucketIndex++;
                advanceToNextNonEmptyBucket();
            }
            return nextElement;
        }

        private void advanceToNextNonEmptyBucket() {
            while (bucketIndex < buckets.length) {
                if (buckets[bucketIndex] != null && !buckets[bucketIndex].isEmpty()) {
                    bucketIterator = buckets[bucketIndex].iterator();
                    break;
                }
                bucketIndex++;
            }
            if (bucketIndex >= buckets.length) {
                bucketIterator = null; // No more elements
            }
        }
    }
}