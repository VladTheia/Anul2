import java.util.*;

public abstract class ItemList {
    //Comparator used for sorting
    Comparator comparator;

    //Members of the list
    protected Node head;
    protected int length;

    public ItemList() {
        this.head = null;
        this.length = 0;
    }

    //Node class for the doubly linked list
    static class Node<Item> {
        private Node next, prev;
        private Item item;

        public Node(Item item) {
            this.item = item;
            next = null;
            prev = null;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }
    }

    //Check if it works
    class ItemIterator implements ListIterator<Item> {
        Node<Item> next;
        Node<Item> prev;
        Node<Item> lastReturned;
        int index = 0;

        public ItemIterator(int index) {
            next = head;
            while (this.index != index) {
                next = next.getNext();
                this.index++;
            }
            if (index != 0)
                prev = next.getPrev();
        }

        @Override
        public boolean hasNext() {
            if (next != null)
                return true;
            return false;
        }

        @Override
        public Item next() {
            Item currentItem = next.getItem();
            lastReturned = next;
            prev = next;
            next = next.getNext();
            index++;
            return currentItem;
        }

        @Override
        public boolean hasPrevious() {
            if (prev != null)
                return true;
            return false;
        }

        @Override
        public Item previous() {
            Item currentItem = prev.getItem();
            lastReturned = prev;
            next = prev;
            prev = prev.getPrev();
            index--;
            return currentItem;
        }

        @Override
        public int nextIndex() {
            return index + 1;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (lastReturned.getPrev() == null && lastReturned.getNext() == null) { //see if case is correct
                head = null;
                next = null;
                prev = null;
                index = 0;
                return;
            }
            if (lastReturned.getPrev() == null) {
                head = lastReturned.getNext();
                next = head;
                prev = null;
                head.setPrev(null);
                return;
            }
            if (lastReturned.getNext() == null) {
                lastReturned.getPrev().setNext(null);
                prev = lastReturned.getPrev();
                return;
            }
            prev = lastReturned.getPrev();
            prev.setNext(lastReturned.getNext());
            next.setPrev(lastReturned.getPrev());
        }

        @Override
        public void set(Item o) {
            lastReturned.setItem(o);
        }

        @Override
        public void add(Item o) { //complete
            Node current = new Node(o);
            if (prev == null && next == null) {
                head = current;
                next = head;
            }
            next.setNext(current);
            current.setPrev(prev);
        }
    }

    //Doubly linked list methods
    public boolean isEmpty() {
        return length == 0;
    }

    public int length() {
        return length;
    }

    //Add a new item, using the comparator in order to keep the list sorted
    public boolean add(Item element) {
        Node newNode = new Node(element);
        Node aux, auxNext;
        if (head == null) {
            head = newNode;
            length++;
            return true;
        } else if (comparator.compare(element, head.item) <= 0) {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
            length++;
            return true;
        } else {
            aux = head;
            auxNext = head.getNext();
            while (auxNext != null) {
                if (comparator.compare(element, aux.getItem()) >= 0 &&
                    comparator.compare(element, auxNext.getItem()) <= 0) {
                    aux.setNext(newNode);
                    newNode.setPrev(aux);
                    newNode.setNext(auxNext);
                    auxNext.setPrev(newNode);
                    length++;
                    return true;
                } else {
                    aux = auxNext;
                    auxNext = auxNext.getNext();
                }
            }
            aux.setNext(newNode);
            newNode.setPrev(aux);
            length++;
            return true;
        }
    }

    //Adds all the items from the given collection
    public boolean addAll(Collection<? extends Item> c) {
        for (Item item : c) {
            add(item);
        }
        return true;
    }

    //Returns the item at the given index
    public Item getItem(int index) { return getNode(index).getItem(); }

    //Returns the node at the given index
    public Node<Item> getNode(int index) {
        if (head != null) {
            Node aux = head;
            while (aux.getNext() != null) {
                if ( indexOf(aux) == index)
                    return aux;
                aux = aux.getNext();
            }//check the last node
            if ( indexOf(aux) == index)
                return aux;
        }
        return null;
    }

    //Returns the index of the given item
    public int indexOf(Item item) {
        int index = 0;
        if (head != null) {
            Node aux = head;
            Item auxItem;
            while (aux.getNext() != null) {
                auxItem = (Item) aux.getItem();
                if ( auxItem.getID() == item.getID())
                    return index;
                aux = aux.getNext();
                index++;
            } //Checking the last position
            auxItem = (Item) aux.getItem();
            if ( auxItem.getID() == item.getID())
                return index;
        }
        return -1;
    }

    //Returns the index of the given node
    public int indexOf(Node<Item> node) { return indexOf(node.getItem()); }

    //States if the list contains the given node
    public boolean contains(Node<Item> node) { return contains(node.getItem()); }

    //States if the list contains the given item
    public boolean contains(Item item) {
        if (head != null) {
            Node aux = head;
            Item auxItem;
            while (aux.getNext() != null) {
                auxItem = (Item) aux.getItem();
                if ( auxItem.getID() == item.getID())
                    return true;
                aux = aux.getNext();
            } //cheking the last item
            auxItem = (Item) aux.getItem();
            if ( auxItem.getID() == item.getID())
                return true;
        }
        return false;
    }

    //Removes the node at the given index
    public Item remove(int index) {
        Item removedItem = getItem(index);
        remove(getItem(index));
        return removedItem;
    }

    //Removes the given item if possible
    public boolean remove(Item item) {
        ListIterator<Item> it = listIterator();
        while (it.hasNext()) {
            Item aux = it.next();
            if (aux.getID() == item.getID()) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    //Removes all the items given in the collection //TO-DO
    public boolean removeAll(Collection<? extends Item> collection) {
        for (Item item : collection) {
            if (contains(item)) {
                remove(indexOf(item));
            } else {
                return false;
            }
        }
        return true;
    }

    //Returns an item iterator starting from the given index
    public ListIterator<Item> listIterator(int index) {
        return new ItemIterator(index);
    }

    //Returns an item iterator starting from 0
    public ListIterator<Item> listIterator() {
        return new ItemIterator( 0);
    }

    //Returns the total price of the items in the list
    public float getTotalPrice() {
        float total = 0;
        if (head != null) {
            ListIterator<Item> it = listIterator();
            while (it.hasNext()) {
                total += it.next().getPrice();
            }
        }
        return total;
    }
}
