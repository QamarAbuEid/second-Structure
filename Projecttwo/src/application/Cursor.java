package application;

public class Cursor<T extends Comparable<T>> {
	//The Attributes Field 
	private Node<T>[] cursorArray;
	
	
      //The constructor 
	public Cursor(int size) {
		super();
		this.cursorArray = new Node[size];
		initialization();
	}

	public void initialization() {
		for (int i = 0; i < cursorArray.length - 1; i++)
			cursorArray[i] = new Node<>(null, i + 1);
		cursorArray[cursorArray.length - 1] = new Node<>(null, 0);
	}

	

	public int malloc() {
		
		int p = cursorArray[0].getNext();
		cursorArray[0].setNext(cursorArray[p].getNext());
		return p;
	}

	public void free(int p) {
		cursorArray[p] = new Node<>(null, cursorArray[0].getNext());
		cursorArray[0].setNext(p);
	}

	public boolean isNull(int l) {
		return cursorArray[l] == null;
	}

	public boolean isEmpty(int l) {
		return cursorArray[l].getNext() == 0;
	}

	public boolean isLast(int p) {
		return cursorArray[p].getNext() == 0;
	}

	public int createList() {
		
		int l = malloc();
		if (l == 0)
			System.out.println("Error: Out of space!!!");
		else
			cursorArray[l] = new Node("-", 0);
		return l;
	}

	public void insertAtHead(T data, int l) {
		if (isNull(l)) {// list not created
			return;}
		int p = malloc(); 
		if (p != 0) {
			
			cursorArray[p] = new Node<>(data, cursorArray[l].getNext()); 
			//System.out.print(data);
			cursorArray[l].setNext(p);  
			
		} else
			System.out.println("Error: Out of space!!!");
	}

	public Node<T> deleteFromHead(int l) {
		if (!isNull(l) && !isEmpty(l)) {
			int p = cursorArray[l].getNext();
			cursorArray[l].setNext(cursorArray[p].getNext());
			free(p);
		}
		return null;
	}
	public Node<T> getHead(int l) {
	    if (!isNull(l) && !isEmpty(l)) {
	        int headIndex = cursorArray[l].getNext();
	        return cursorArray[headIndex];
	    }
	    return null;
	}

	public void traversList(int l) {
		System.out.print("list_" + l + " --> ");
		while (!isNull(l) && !isEmpty(l)) {
			l = cursorArray[l].getNext();
			System.out.print(cursorArray[l] + " --> ");
		}
		System.out.println("null");
	}

	public int find(T data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			l = cursorArray[l].getNext();
			if (cursorArray[l].getData().equals(data))
				return l;
		}
		return -1; // not found
	}

	

	

	public void insertLast(T data, int l) {
		if (isNull(l)) // list not created
			return;
		int p = malloc();
		if (p != 0) {
			cursorArray[p] = new Node<>(data, 0);
			while (!isLast(l)) // cursorArray[i].getNext() != 0
				l = cursorArray[l].getNext();
			cursorArray[l].setNext(p);
		} else
			System.out.println("Error: Out of space!!!");
	}

	

	//The clear Method it is the same with initialize method 
	public void clear() {
		for (int i = 0; i < cursorArray.length - 1; i++) {
			cursorArray[i] = new Node<>(null, i + 1);
		}
		 cursorArray[cursorArray.length - 1] = new Node<>(null, 0);
	}
}