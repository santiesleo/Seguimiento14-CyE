package model;

public class Queue<T> implements IQueue<T>{
    private Node<T> front;
    private Node<T> back;
    private int size;

    public Queue() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    @Override
    public void enqueue(T value){
        Node<T> node = new Node<>(value);
        if(isEmpty()){
            this.front = node;
        }else{
            this.back.setNext(node);
        }
        this.back=node;
        this.size++;
    }

    @Override
    public T dequeue() throws QueueException {
        if(!isEmpty()){
            Node<T> aux = this.front;
            front = front.getNext();
            size--;
            return aux.getValue();
        }else throw new QueueException("Empty queue");
    }

    @Override
    public T front() throws QueueException{
        if(!isEmpty()){
            return this.front.getValue();
        }else throw new QueueException("Empty queue");
    }

    @Override
    public boolean isEmpty(){
        return size==0;
    }

    @Override
    public int size(){
        return this.size;
    }

    public String print(){
        String s = "";
        Node<T> aux = this.front;
        while(aux != null){
            s += aux.getValue().toString();
            aux = aux.getNext();
        }
        return s;
    }

}
