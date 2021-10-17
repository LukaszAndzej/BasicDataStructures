package com.company;

class Node {
    Node next;
    Node preview;
    int element;

    public Node(int element) {
        this.element = element;
    }
}

class LinkedListException extends Exception {
    public LinkedListException(String error) {
        super(error);
    }
}

class DoublyLinkedListLogic {

    protected Node head;
    protected Node trailer;

    protected boolean emptyList() { return head == null;}
    protected boolean lastNode(Node node) { return (node == trailer);}
    protected boolean firstNode(Node node) { return (node == head);}
    protected boolean nodeIsNull(Node node) { return (node == null);}
    protected boolean checkElement(Node node, int element) { return (node.element != element);}
    protected boolean nodeCondition(Node node, int element) { return (!nodeIsNull(node) && checkElement(node, element));}
}

public class DoublyLinkedList extends DoublyLinkedListLogic {

    private Node setHeadAndTrailer(int element) {
        trailer = head = new Node(element);
        return head;
    }

    public Node insertLast(int newElement) {

        if(emptyList()) return setHeadAndTrailer(newElement);

        Node newNode = new Node(newElement);
        newNode.preview = trailer;
        trailer.next = newNode;
        trailer = newNode;

        return newNode;
    }

    public Node insertFirst(int newElement) {
        if(emptyList()) return setHeadAndTrailer(newElement);

        Node newNode = new Node(newElement);
        newNode.next = head;
        head.preview = newNode;
        head = newNode;

        return newNode;
    }

    public Node insertAfter(Node node, int newElement) throws LinkedListException {

        if(nodeIsNull(node)) throw new LinkedListException("Node is null");
        if(lastNode(node)) return insertLast(newElement);

        Node newNode = new Node(newElement);

        newNode.element = newElement;
        newNode.preview = node;
        newNode.next = node.next;
        (node.next).preview = newNode;
        node.next = newNode;

        return newNode;
    }

    public Node insertBefore(Node node, int newElement) throws LinkedListException {

        if (nodeIsNull(node)) throw new LinkedListException("Node not exist!");
        if (firstNode(node)) return insertFirst(newElement);

        Node newNode = new Node(newElement);

        newNode.next = node;
        newNode.preview = node.preview;
        (node.preview).next = newNode;
        node.preview = newNode;

        return newNode;
    }

    public int remove(Node node) throws LinkedListException {

        if (nodeIsNull(node)) throw new LinkedListException("Node not exist!");

        int removeElement = node.element;

        if (lastNode(node)) trailer = node.preview;
        if (firstNode(node)) head = node.next;

        if (!nodeIsNull(node.preview)) (node.preview).next = node.next;
        if (!nodeIsNull(node.next)) (node.next).preview = node.preview;

        node.preview = null;
        node.next = null;

        return removeElement;
    }

    public Node findNode(int element) {
        Node current = head;
        while ( nodeCondition(current, element) ) current = current.next;

        return current;
    }

    public void revers() throws LinkedListException {

        Node temporaryHead = null;

        for (int i = 0; head != trailer; i++) {
            if (i == 0) temporaryHead = insertFirst(remove(trailer));
            else {
                insertAfter(head,remove(trailer));
                head = head.next;
            }
        }

        head = temporaryHead;
    }

    public void printList() {
        System.out.println("Print:");
        for (Node current = head; !nodeIsNull(current); current = current.next) {
            if (current != head) System.out.print(" -> ");
            System.out.print("(" + current.element + ")");
        }
    }

}
