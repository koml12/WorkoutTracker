package com.example.mohit31.workouttracker.utils;

/**
 * Created by mohit31 on 10/11/17.
 */

/**
 * Class representing a queue, a FIFO (first in, first out) data structure.
 * This particular implementation utilizes a circular linked list of Nodes defined in the Node class of the package.
 *
 * @param <T>                           Generic type for variables in the queue.
 */
public class Queue<T> {
    private Node<T> rear;
    private int size;

    public Queue() {
        rear = null;
        size = 0;
    }

    /**
     * Adds a Node of type T to the rear of the Queue.
     *
     * @param item
     */
    public void enqueue(T item) {
        Node<T> newItem = new Node<>(item, null);
        if(rear == null) {
            newItem.next = newItem;
        } else {
            newItem.next = rear.next;
            rear.next = newItem;
        }
        rear = newItem;
        size++;
    }

    /**
     * Pops the front Node of the queue, and returns its data field.
     *
     * @return
     */
    public T dequeue() {
       if (size == 0) {
           return null;
       }
       Node<T> nextNode = rear.next;
       T data = nextNode.data;
       if (rear == rear.next) {
           rear = null;
       } else {
           rear.next = rear.next.next;
       }
       return data;
    }

    public int getSize() {
        return size;
    }
}
