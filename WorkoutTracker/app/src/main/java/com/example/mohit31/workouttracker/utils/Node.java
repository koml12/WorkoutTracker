package com.example.mohit31.workouttracker.utils;

/**
 * Created by mohit31 on 10/11/17.
 */

/**
 * Class representing a generic Node of a linked list.
 * Can be used for multiple applications, so there is a generic type.
 *
 * @param <T>                   Generic type for the class
 */
public class Node<T> {
    T data;
    Node next;

    /**
     * Creates a new Node with data='data' and next='next'
     *
     * @param data              The data the Node object holds.
     * @param next              A pointer to the next Node.
     */
    public Node(T data, Node next) {
        this.data = data;
        this.next = next;
    }

    /**
     * Returns a simple string representation of a Node, in this case, just the data field.
     * Note that since data can be ANY object, it is not sufficient to just return data - we must return the String
     * representation of data, which could be defined where the type T is defined.
     *
     * @return                  The data field of the Node;
     */
    @Override
    public String toString() {
        return data.toString();
    }
}
