/**
 * Clasa ArboreBinar
 *
 * @author Balint Corneliu Madalin
 * @version 1.0.0 - 7 mai 2011
 */

import java.util.ArrayList;

public class ArboreBinar {
    private Node root;
    private ArrayList lista = new ArrayList();
    private ArrayList lista_key = new ArrayList();
    
    public Node find(long key) {
        Node current = root;

        if (current == null) return null; 
        while (current.iData != key) { 
           if (key < current.iData) current = current.leftChild;
              else current = current.rightChild;
           if (current == null) return null; 
        }
        return current; 
    }

    public ArrayList getListaData() {
        return lista;
    }

    public ArrayList getListaKey() {
        return lista_key;
    }
    
    public void insert(long id, String dd) {
    	Node newNode = new Node();
        newNode.iData = id;
        newNode.dData = dd;
        lista_key.add(id);
        lista.add(dd);

        if (root == null) root = newNode;
           else {
             Node current = root; 
             Node parent;
             while (true) {
                parent = current;
                if (id < current.iData) {
                   current = current.leftChild;
                   if (current == null) { 
                      parent.leftChild = newNode;
                      return;
                   }
                } else {
                    current = current.rightChild;
                    if (current == null) { 
                       parent.rightChild = newNode;
                       return;
                    }
                  }
             }
           }
    }
}

class Node {
    long   iData;      // data used as key value
    String dData;      // other data
    Node   leftChild;  // this node's left child
    Node   rightChild; // this node's right child
}