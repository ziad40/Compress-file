import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Main {

    public static void main(String[] args) throws IOException {
        if (Objects.equals(args[0], "c")){
            int n = Integer.parseInt(args[2]);
            byte[] buffer = new byte[n*512];
            Map<ByteArr,Integer> frequences = new HashMap<>();
            FileInputStream fileRead = new FileInputStream((args[1]));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileRead);
            int rc = bufferedInputStream.read(buffer);
            while(rc != -1)
            {
                ByteArr arr = new ByteArr(new byte[n]);
                int index = 0;
                for (int i= 0; i < rc;i++){
                    arr.array[index] = buffer[i];
                    index++;
                    if (index == n){
                        frequences.put(arr, frequences.getOrDefault(arr,0)+1);
                        index = 0;
                        arr = new ByteArr(new byte[n]);
                    }
                }
                rc = bufferedInputStream.read(buffer);
            }

            Huffman coder = new Huffman();
            Node root = coder.createTree(frequences);
            coder.createMap(root,0);
            Compress c = new Compress();
            c.compressFile(coder, n,args[1]);
        } else if (Objects.equals(args[0], "d")) {
            DeCompressed d = new DeCompressed();
            d.decompressFile(args[1]);
        }
    }
}