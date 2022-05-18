/**
 * @author gsotelo
 * A stack ADT implemented using doubly-linked lists
 */
public class DLStack<T> implements DLStackADT<T>{
    private DoubleLinkedNode<T> top;
    private int numItems;

    /**
     * Default constructor for the stack
     */
    public DLStack() {
        top = null;
        numItems = 0;
    }

    /**
     * Add an element to the stack
     * @param element element to be pushed onto stack
     */
    public void push(T element) {
        DoubleLinkedNode<T> temp = new DoubleLinkedNode<T>(element);
        if (top != null) {
            top.setNext(temp);
            temp.setPrevious(top);
        }
        top = temp;
        numItems++;
    }

    /**
     * Remove the element from the top of the stack and return the element
     * @return the element removed from the stack
     * @throws EmptyStackException if stack has no elements
     */
    public T pop() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException("The stack is empty");
        T result = top.getElement();
        top = top.getPrevious();
        numItems--;
        return result;
    }

    /**
     * Check the element at the top of the stack without removing it
     * @return the element at the top of the stack
     */
    public T peek() {
        if (isEmpty()) throw new EmptyStackException("The stack is empty");
        return top.getElement();
    }

    /**
     * Remove a single element within the stack starting from the top
     * @param k index of element in the stack that is to be removed
     * @return the element removed from the stack
     * @throws InvalidItemException when the index of the element to be removed is larger than the size of the stack
     */
    public T pop(int k) throws InvalidItemException {
        if (k > numItems || k <= 0) throw new InvalidItemException("Invalid index");

        DoubleLinkedNode<T> curr = top;
        T result = curr.getElement();

        int itemsTraversed = 1;
        while (curr != null) {
            if (k == 1) {
                return pop();
            } else if (itemsTraversed == k) {
                if (curr.getPrevious() == null) {
                    curr.setPrevious(null);
                } else {
                    curr.getPrevious().setNext(curr.getNext());
                    curr.getNext().setPrevious(curr.getPrevious());
                }
                result = curr.getElement();
                break;
                } else {
                    curr = curr.getPrevious();
                    itemsTraversed++;
                }
            }
        numItems--;
        return result;
    }

    /**
     * Check if the stack is empty
     * @return true or false depending on whether the stack contains any elements
     */
    public boolean isEmpty() {
        return (top == null);
    }

    /**
     * Check the size of the stack
     * @return an int equal to the number of elements in the stack
     */
    public int size() {
        return numItems;
    }

    /**
     * Presents the stack and all elements contained inside as a string
     * @return a string representation of the stack
     */
    public String toString() {
        if (!isEmpty()) {
            DoubleLinkedNode<T> curr = top;
            String result = "[" + curr.getElement();
            curr = curr.getPrevious();

            while (curr != null) {
                result += " " + curr.getElement();
                curr = curr.getPrevious();
            }
            result += "]";
            return result;
        } else
            return "";
    }

    /**
     * Returns the top node of the stack
     * @return the first element of the stack as pointed by the top pointer
     */
    public DoubleLinkedNode<T> getTop() {
        return top;
    }
}