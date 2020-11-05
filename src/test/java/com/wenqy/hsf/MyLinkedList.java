package com.wenqy.hsf;

class MyLinkedList {
    class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
            this.next = null;
        }
    }

    Node head;

    /** Initialize your data structure here. */
    public MyLinkedList() {

    }
    
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        int i = 0;
        for (Node cur = this.head; cur != null; cur = cur.next) {
            if (i++ == index) {
                return  cur.val;
            }
        }
        return -1;
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        Node node = new Node(val);
        node.next = head;
        head = node;
    }
    
    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        int i = 0;
        Node cur = this.head;
        Node node = new Node(val);
        if (cur == null) {
            this.head = node;
            return;
        }
        for (; cur.next != null; cur = cur.next) {
        }
        cur.next = node;
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        int i = 0;
        Node node = new Node(val);
        if (index == 0) {
            node.next = head;
            head = node;
            return;
        }
        for (Node cur = this.head; cur != null; cur = cur.next) {
            if (i+1 == index) {
                node.next = cur.next;
                cur.next = node;
                break;
            }
            i++;
        }
    }
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        int i = 0;
        Node pre = null;
        if (index == 0) {
            if (head != null) {
                Node node = head;
                head = head.next;
                node.next = null;
            }
            return;
        }
        for (Node cur = this.head; cur != null; cur = cur.next) {
            if (i+1 == index) {
                pre = cur;
            } else if (i == index) {
                pre.next = cur.next;
                break;
            }
            i++;
        }
    }

    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList();
        linkedList.addAtHead(1);
        linkedList.addAtTail(3);
        linkedList.addAtIndex(1,2);   //链表变为1-> 2-> 3
        linkedList.get(1);            //返回2
        linkedList.deleteAtIndex(1);  //现在链表是1-> 3
        linkedList.get(1);            //返回3
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
