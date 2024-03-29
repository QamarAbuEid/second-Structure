package application;

public class CursorStack<T extends Comparable<T>> implements StackADT<T> {
    private Cursor<T> cursorstack;
    private int list; // Index of the list associated with this stack
    private int count = 0;

    //here the constructor of the CursorStack
    //Such That in each time we Creat a new Stack we Should Creat a new list in the Cursor Array
    public CursorStack(int size) {
        cursorstack = new Cursor<>(size);
        list = cursorstack.createList(); 
    }

    @Override
    public void push(T data) {
        cursorstack.insertAtHead(data, list);
        count++;
    }

    @Override
    public T pop() {
        if (!isEmpty()) {
            Node<T> head = cursorstack.getHead(list);
            T poppedData = head.getData();
            cursorstack.deleteFromHead(list);
            count--;
            return poppedData;
        } else {
            // Return null without printing an error message
            return null;
        }
    }



    @Override
    public T peek() {
        if (!isEmpty()) {
            Node<T> head = cursorstack.getHead(list);
            return head.getData();
        }
        return null;
    }


	@Override
	public boolean isEmpty() {
		return cursorstack.isEmpty(list);

	}

	@Override
	public void clear() {
		cursorstack.clear();

	}
	
}
