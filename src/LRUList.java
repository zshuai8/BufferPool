/**
 * This provides implementation for some of the LList methods.
 *
 * @author shuaicheng zhang
 * @param <E>
 * @version 2016.10.23
 *            The type of object the class will store
 */
public class LRUList<E> {

    /**
     * This represents a node in a doubly linked list. This node stores data, a
     * pointer to the node before it in the list, and a pointer to the node
     * after it in the list
     *
     * @param <E>
     *            This is the type of object that this class will store
     */
    private static class Node<E> {
        private Node<E> next;
        private Node<E> previous;
        private E data;

        /**
         * Creates a new node with the given data
         *
         * @param d
         *            the data to put inside the node
         */
        public Node(E d) {
            data = d;
        }

        /**
         * Sets the node after this node
         *
         * @param n
         *            the node after this one
         */
        public void setNext(Node<E> n) {
            next = n;
        }

        /**
         * Sets the node before this one
         *
         * @param n
         *            the node before this one
         */
        public void setPrevious(Node<E> n) {
            previous = n;
        }

        /**
         * Gets the next node
         *
         * @return the next node
         */
        public Node<E> next() {
            return next;
        }

        /**
         * Gets the node before this one
         *
         * @return the node before this one
         */
        public Node<E> previous() {
            return previous;
        }

        /**
         * Gets the data in the node
         *
         * @return the data in the node
         */
        public E getData() {
            return data;
        }
    }

    /**
     * How many nodes are in the list
     */
    private int size;

    /**
     * The first node in the list. THIS IS A SENTINEL NODE AND AS SUCH DOES NOT
     * HOLD ANY DATA. REFER TO init()
     */
    private Node<E> head;

    /**
     * The last node in the list. THIS IS A SENTINEL NODE AND AS SUCH DOES NOT
     * HOLD ANY DATA. REFER TO init()
     */
    private Node<E> tail;
    private int maxsize;
    
    /**
     * Create a new DLList object.
     * @param inputSize is the maximum size
     */
    public LRUList(int inputSize) {
        init(inputSize);
    }

    /**
     * Initializes the object to have the head and tail nodes
     */
    private void init(int inputSize) {
        head = new LRUList.Node<E>(null);
        tail = new LRUList.Node<E>(null);
        head.setNext(tail);
        tail.setPrevious(head);
        size = 0;
        maxsize = inputSize;
    }

    /**
     * Checks if the array is empty
     *
     * @return true if the array is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Gets the number of elements in the list
     *
     * @return the number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Removes all of the elements from the list
     */
    public void clear() {
        init(size);
    }

    /**
     * Gets the object at the given position
     *
     * @param index
     *            where the object is located
     * @return The object at the given position
     * @throws IndexOutOfBoundsException
     *             if there no node at the given index
     */
    public E get(int index) {
        return getNodeAtIndex(index).getData();
    }

    /**
     * Adds the object to the position in the list
     *
     * @param obj
     *            the object to add
     * @throws IndexOutOfBoundsException
     *             if index is less than zero or greater than size
     * @throws IllegalArgumentException
     *             if obj is null
     */
    public void add(E obj) {

        Node<E> addition = new Node<E>(obj);
        addition.setNext(head.next());
        addition.setPrevious(head);
        head.setNext(addition);
        head.next.setPrevious(addition);
        size++;
    }
    
    /**
     * 
     * @return true if the list is full
     */
    public boolean isFull() {
        return size == maxsize;
    }

    /**
     * gets the node at that index
     * 
     * @param index
     * @return node at index
     */
    private Node<E> getNodeAtIndex(int index) {
        Node<E> current = head.next(); 
        for (int i = 0; i < index; i++) {
            current = current.next();
        }
        return current;
    }

    /**
     * Removes the element at the specified index from the list
     *
     * @throws IndexOutOfBoundsException
     *             if there is not an element at the index
     * @return true if successful
     */
    public E removeEnd() {
        Node<E> nodeToBeRemoved = getNodeAtIndex(size - 1);
        nodeToBeRemoved.previous().setNext(nodeToBeRemoved.next());
        nodeToBeRemoved.next().setPrevious(nodeToBeRemoved.previous());
        size--;
        return nodeToBeRemoved.getData();
    }
       
    /**
     * Returns a string representation of the list If a list contains A, B, and
     * C, the following should be returned "{A, B, C}" (Without the quotations)
     *
     * @return a string representing the list
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        if (!isEmpty()) {
            Node<E> currNode = head.next();
            while (currNode != tail) {
                E element = currNode.getData();
                builder.append(element.toString());
                if (currNode.next != tail) {
                    builder.append(", ");
                }  
                currNode = currNode.next();
            }
        }

        builder.append("}");
        return builder.toString();
    }
    
    /**
     * 
     * @param index is the index of the node to push to the front
     */
    public void pushToFront(int index) {
        
        Node<E> cur = getNodeAtIndex(index);
        cur.previous.setNext(cur.next);
        cur.next.setPrevious(cur.previous);
        cur.setPrevious(head);
        cur.setNext(head.next);
    }
}