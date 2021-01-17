/* *****************************************************************************
 *  Name: mquirosdev
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private DynamicList<Item> list;

    private class DynamicList<Item> {
        public int cursor = 0;
        private Object[] a;

        public DynamicList() {
            this.a = new Object[1];
        }

        public void add(Item value) {
           a[cursor] = value;
           cursor++;
           if (cursor == a.length) { enlarge(); }
        }

        public Item remove() {
            Item result = (Item) a[cursor - 1];
            a[cursor - 1] = null;
            cursor--;
            if (cursor == (a.length/4)) { shrink(); }
            return result;
        }

        public Item get(int idx) {
            if (idx >= cursor) { throw new ArrayIndexOutOfBoundsException(); }
            return (Item) a[idx];
        }

        public int size() {
           return cursor;
        }

        public void set(int idx, Item value) {
            if (idx >= cursor) { throw new ArrayIndexOutOfBoundsException(); }
            a[idx] = value;
        }

        public DynamicList<Item> copy() {
            DynamicList<Item> cloned = new DynamicList<Item>();
            for (int i = 0; i < cursor; i++) {
                cloned.add((Item) a[i]);
            }
            return cloned;
        }

        private void enlarge() {
            Object[] newList = new Object[a.length * 2];
            for (int i = 0; i < cursor; i++) {
                newList[i] = a[i];
            }
            a = newList;
        }

        private void shrink() {
            Object[] newList = new Object[a.length / 2];
            for (int i = 0; i < cursor; i++) {
                newList[i] = a[i];
            }
            a = newList;
        }
    }

    private class RandomIterator<Item> implements Iterator<Item> {
        private DynamicList<Item> iterableList;

        public RandomIterator(DynamicList<Item> iterableList) {
            this.iterableList = iterableList.copy();
        }

        public boolean hasNext() {
            return iterableList.size() > 0;
        }

        public Item next() {
            int size = iterableList.size();
            if (size == 0) { throw new NoSuchElementException(); }
            Item item = (size > 1) ? deque() : dequeLast();
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private Item deque() {
            int size = iterableList.size();
            int index = StdRandom.uniform(size - 1);
            Item item = iterableList.get(index);
            Item lastItem = iterableList.remove();
            iterableList.set(index, lastItem);
            return item;
        }

        private Item dequeLast() {
            Item item = iterableList.get(0);
            iterableList.remove();
            return item;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        list = new DynamicList<Item>();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return list.size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return list.size();
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        list.add(item);
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        Item item = (size() > 1) ? dequeueItem() : dequeueLastItem();
        return item;
    }

    private Item dequeueItem() {
        int size = list.size();
        int index = StdRandom.uniform(size - 1);
        Item item = list.get(index);
        Item lastItem = list.remove();
        list.set(index, lastItem);
        return item;
    }

    private Item dequeueLastItem() {
        Item item = list.get(0);
        list.remove();
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        int index = StdRandom.uniform(size());
        return list.get(index);
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator<Item>(list);
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        for (int i : queue) {
            System.out.println(i);
        }
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
    }

}