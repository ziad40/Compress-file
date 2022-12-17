import java.io.Serializable;

public class Node implements Comparable<Node>, Serializable {
    Integer frequency;
    ByteArr b;
    Node left = null;
    Node right = null;
    public Node(int frec,ByteArr b){
        frequency =  frec;
        this.b  = b;
    }
    public Node(int frec,Node l , Node r){
        frequency =  frec;
        left = l;
        right = r;
    }
    public boolean isLeaf(){
        return this.left ==null && this.right == null;
    }

    @Override
    public int compareTo(Node n) {
        return (int) (this.frequency - n.frequency);
    }
}
