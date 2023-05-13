package model;

public interface IQueue <T>{
    public void enqueue(T value);
    public T dequeue() throws QueueException;
    public T front() throws QueueException;
    public boolean isEmpty();
    public int size();
    public String print();

}
