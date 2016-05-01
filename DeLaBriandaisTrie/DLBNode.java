/**
 * @Author Anthony Poerio - (adp59@pitt.edu)
 * University of Pittsburgh, Spring 2016
 * CS1501 - Algorithm Implementation
 * DLBNodeClass
 */
public class DLBNode
{

    char data='\0';        // data value, a character
    boolean flag=false;    // flag=true IFF the word is valid
    DLBNode next=null;     // next node at same level
    DLBNode prev=null;     // previous node at same level
    DLBNode parent=null;   // parent node, one level up
    DLBNode child=null;    // child node, one level down

    /**
     * No-args constructor
     */
    DLBNode() {
        this.data='\0';
        this.flag=false;
        this.next=null;
        this.prev=null;
        this.parent=null;
        this.child=null;
    }

    /**
     * Constructor to link to a given parent node, if we have data
     * @param input the data input
     */
    DLBNode(char input) {
        this.data=input;
        this.flag=false;
        this.next=null;
        this.prev=null;
        this.parent=null;
        this.child=null;
    }

    /**
     * Constructor to link to a given parent node, if we have data
     * and a parent to link to
     * @param parentNode The parent node to link to
     * @param input the data input
     */
    DLBNode(DLBNode parentNode, char input) {
        this.data=input;
        this.flag=false;
        this.parent=parentNode;
        this.next=null;
        this.prev=null;
        this.child=null;
    }

    ///////////////////////////////
    ///// GETTERS AND SETTERS /////
    ///////////////////////////////


    public char getData() {
        return data;
    }

    public void setData(char data) {
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public DLBNode getNext() {
        return next;
    }

    public void setNext(DLBNode next) {
        this.next = next;
    }

    public DLBNode getPrev() {
        return prev;
    }

    public void setPrev(DLBNode prev) {
        this.prev = prev;
    }

    public DLBNode getParent() {
        return parent;
    }

    public void setParent(DLBNode parent) {
        this.parent = parent;
    }

    public DLBNode getChild() {
        return child;
    }

    public void setChild(DLBNode child) {
        this.child = child;
    }

}
