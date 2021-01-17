/* *****************************************************************************
 *  Name: mquirosdev
 *  Date:
 *  Description:
 **************************************************************************** */


import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class DynamicList<Item> {
        class Node<Item> {
            public Node<Item> next;
            public Node<Item> previous;
            private Item value;
            public Node(Item value) {
                this.value = value;
            }
        }

        public Node head;
        public Node tail;
        public int size;

        public DynamicList() {
            head = null;
            tail = null;
            size = 0;
        }

        public void addFirst(Item item) {
            Node node =  new Node(item);
            if (size > 0) {
                Node oldHead = head;
                head = node;
                oldHead.previous = node;
                node.next = oldHead;
            } else {
                setFirstNode(node);
            }
            size++;
        }

        public void addLast(Item item) {
            Node node =  new Node(item);
            if (size > 0) {
                Node oldTail = tail;
                tail = node;
                oldTail.next = node;
                node.previous = oldTail;
            } else {
                setFirstNode(node);
            }
            size++;
        }

        public Item removeFirst() {
            Node result = head;
            head = result.next;
            result.next = null;
            if (head != null) { head.previous = null; }
            if (size == 1) { removeLastNode(); }
            size--;
            return (Item) result.value;
        }

        public Item removeLast() {
            Node result = tail;
            tail = result.previous;
            if (tail != null) { tail.next = null; }
            result.previous = null;
            if (size == 1) { removeLastNode(); }
            size--;
            return (Item) result.value;
        }

        private void setFirstNode(Node node) {
            head = node;
            tail = node;
        }

        private void removeLastNode() {
            head = null;
            tail = null;
        }
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private DynamicList<Item>.Node<Item> currentNode;
        private DynamicList<Item> iterableList;

        public DequeIterator(DynamicList<Item> iterableList) {
            currentNode = iterableList.head;
            this.iterableList = iterableList;
        }

        public boolean hasNext() {
            return currentNode != null;
        }

        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            DynamicList<Item>.Node<Item> result = currentNode;
            currentNode = currentNode.next;
            return result.value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private DynamicList<Item> list;

    public Deque() {
        this.list = new DynamicList<Item>();
    }

    public boolean isEmpty() {
        return list.size == 0;
    }

    public int size() {
        return list.size;
    }

    public void addFirst(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        list.addFirst(item);
    }

    public void addLast(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        list.addLast(item);
    }

    public Item removeFirst() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        return list.removeFirst();
    }

    public Item removeLast() {
        if (isEmpty()) { throw new NoSuchElementException(); }
       return list.removeLast();
    }

    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(list);
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(0);
        deque.addLast(9);
        deque.addLast(1);
        deque.addFirst(8);
        deque.addLast(2);
        deque.addFirst(7);
        deque.addLast(6);
        deque.addFirst(3);
        deque.addLast(5);
        deque.addFirst(4);

        for (int number : deque) {
            System.out.println(number);
        }
    }

}