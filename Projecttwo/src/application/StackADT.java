package application;

public interface StackADT<T> {
	
	public void push(T data);

	public T pop();

	public T peek();

	public boolean isEmpty();

	void clear();
}
