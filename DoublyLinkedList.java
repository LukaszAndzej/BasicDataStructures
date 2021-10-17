package com.company;

class Node<T> {
    Node<T> next;
    Node<T> preview;
    T element;

    public Node(T element) {
        this.element = element;
    }
}

class LinkedListException extends Exception {
    public LinkedListException(String error) {
        super(error);
    }
}

class DoublyLinkedListLogic<T> {

    protected Node<T> head;
    protected Node<T> trailer;

    protected boolean emptyList() { return head == null;}
    protected boolean lastNode(Node<T> node) { return (node.equals(trailer));}
    protected boolean firstNode(Node<T> node) { return (node.equals(head));}
    protected boolean nodeIsNull(Node<T> node) { return (node == null);}
    protected boolean checkElement(Node<T> node, T element) { return (!node.element.equals(element));}
    protected boolean nodeCondition(Node<T> node, T element) { return (!nodeIsNull(node) && checkElement(node, element));}
}

public class DoublyLinkedList<T> extends DoublyLinkedListLogic<T> {

    private Node<T> setHeadAndTrailer(T element) {
        trailer = head = new Node<>(element);
        return head;
    }

    public Node<T> first() { return head;}
    public Node<T> last() { return trailer;}
    public Node<T> before(Node<T> node) { return node.preview;}
    public Node<T> after(Node<T> node) { return node.next;}
    public T element(Node<T> node) { return node.element;}

    public Node<T> insertLast(T newElement) {

        if(emptyList()) return setHeadAndTrailer(newElement);

        Node<T> newNode = new Node<>(newElement);
        newNode.preview = last();
        trailer.next = newNode;
        trailer = newNode;

        return newNode;
    }

    public Node<T> insertFirst(T newElement) {
        if(emptyList()) return setHeadAndTrailer(newElement);

        Node<T> newNode = new Node<>(newElement);
        newNode.next = first();
        head.preview = newNode;
        head = newNode;

        return newNode;
    }

    public Node<T> insertAfter(Node<T> node, T newElement) throws LinkedListException {

        if(nodeIsNull(node)) throw new LinkedListException("Node is null");
        if(lastNode(node)) return insertLast(newElement);

        Node<T> newNode = new Node<>(newElement);

        newNode.element = newElement;
        newNode.preview = node;
        newNode.next = after(node);
        (node.next).preview = newNode;
        node.next = newNode;

        return newNode;
    }

    public Node<T> insertBefore(Node<T> node, T newElement) throws LinkedListException {

        if (nodeIsNull(node)) throw new LinkedListException("Node not exist!");
        if (firstNode(node)) return insertFirst(newElement);

        Node<T> newNode = new Node<>(newElement);

        newNode.next = node;
        newNode.preview = before(node);
        (node.preview).next = newNode;
        node.preview = newNode;

        return newNode;
    }

    public T remove(Node<T> node) throws LinkedListException {

        if (nodeIsNull(node)) throw new LinkedListException("Node not exist!");

        T removeElement = element(node);

        if (lastNode(node)) trailer = before(node);
        if (firstNode(node)) head = after(node);

        if (!nodeIsNull(node.preview)) (node.preview).next = after(node);
        if (!nodeIsNull(node.next)) (node.next).preview = before(node);

        node.preview = null;
        node.next = null;

        return removeElement;
    }

    public Node<T> findNode(T element) {
        Node<T> current = first();
        while ( nodeCondition(current, element) ) current = after(current);

        return current;
    }

    public void revers() throws LinkedListException {

        Node<T> temporaryHead = null;

        for (int i = 0; first() != last(); i++) {
            if (i == 0) temporaryHead = insertFirst(remove(last()));
            else {
                insertAfter(first(),remove(last()));
                head = after(first());
            }
        }

        head = temporaryHead;
    }

    public void printList() {
        System.out.println("Print:");
        for (Node<T> current = first(); !nodeIsNull(current); current = after(current)) {
            if (current != first()) System.out.print(" -> ");
            System.out.print("(" + current.element + ")");
        }
    }

}
