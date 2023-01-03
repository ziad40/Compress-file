import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
    Node Root ;
    Map<ByteArr, ByteArr> keyMap = new HashMap<>();
    Map<ByteArr, ByteArr> reverseKeyMap = new HashMap<>();

    public Node createTree(Map<ByteArr,Integer> frequences){

        System.out.println("creating tree ....");
        int n = frequences.size();
        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (Map.Entry<ByteArr,Integer> e : frequences.entrySet()){
            if (e.getValue() != 0)
                queue.add(new Node(e.getValue(), e.getKey()));
        }
        for (int i= 1; i < n ;i++){
            Node n1 = queue.poll();
            Node n2 = queue.poll();
            if (n1 == null || n2 == null){
                continue;
            }
            queue.add(new Node(n1.frequency+n2.frequency,n1,n2));
        }


        System.out.println("tree is created");
        return Root = queue.poll();
    }

    public void createMap (Node root ,int type){
        System.out.println("creating map .....");
        StringBuilder str = new StringBuilder();
        Node n = root;
        _createMap(n,str,type);
        System.out.println("map is created");
    }

    private void _createMap(Node root,StringBuilder s,int type){
        if (root.isLeaf()) {
            int num_bytes ;
            if (s.length() % 8 != 0){
                num_bytes = s.length()/8+2;
            }else{
                num_bytes = s.length()/8+1;
            }
            byte[] bytesArray = new byte[num_bytes];
            bytesArray[num_bytes-1] = (byte) s.length();
            for (int j=0 ;j < num_bytes-1; j++){
                byte num= 0;
                for (int i = j*8; i < s.length() && i < j*8+8 ;i++){ // 01000000 01 -> 64 , 1 , 10
                    if (s.charAt(i) == '1')
                        num +=Math.pow(2,i-j*8);
                }
                bytesArray[j] = num;
            }
            ByteArr B = new ByteArr(bytesArray);
            if (type == 0)
                reverseKeyMap.put(root.b,B);
            else
                keyMap.put(B,root.b);
        }else{
            StringBuilder r = new StringBuilder(s);
            _createMap(root.left, s.insert(0,'0'),type);
            _createMap(root.right, r.insert(0,'1'),type);
        }
    }



}
