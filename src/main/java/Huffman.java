import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
    Node Root ;
//    Map<ByteArr, ByteArr> keyMap = new HashMap<>();
    Map<ByteArr, ByteArr> reverseKeyMap = new HashMap<>();

    private Node createTree(Map<ByteArr,Integer> frequences){

        System.out.println("creating tree ....");

        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (Map.Entry<ByteArr,Integer> e : frequences.entrySet()){
            if (e.getValue() != 0)
                queue.add(new Node(e.getValue(), e.getKey()));
        }

        while (queue.size() > 1){
            Node l = queue.poll();
            Node r = queue.poll();
            assert r != null;
            Node n = new Node(l.frequency+r.frequency,l,r);
            queue.add(n);
        }
        System.out.println("tree is created");
        assert queue.peek() != null;
        Root = new Node (queue.peek().frequency,queue.peek().left,queue.peek().right);
        return queue.peek();
    }

    public void createMap (Map<ByteArr,Integer> frequences){
        System.out.println("creating map .....");
        Node root = createTree(frequences);
        StringBuilder str = new StringBuilder();
        _createMap(root,str);
        System.out.println("map is created");
    }

    private void _createMap(Node root,StringBuilder s){
        if (root.isLeaf()) {
            int num_bytes = s.length()/8+2;
            byte[] bytesArray = new byte[num_bytes];
            bytesArray[num_bytes-1] = (byte) s.length();
            for (int j=0 ;j < num_bytes-1; j++){
                byte num=0;
                for (int i = j*8; i < s.length() && i < j*8+8 ;i++){
                    if (s.charAt(i) == '1')
                        num +=Math.pow(2,i-j*8);
                }
                bytesArray[j] = num;
            }
            ByteArr B = new ByteArr(bytesArray);
            //keyMap.put(B,root.b);
            reverseKeyMap.put(root.b,B);
        }else{
            StringBuilder r = new StringBuilder(s);
            _createMap(root.left, s.insert(0,'0'));
            _createMap(root.right, r.insert(0,'1'));
        }
    }



}
