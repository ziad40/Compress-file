import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class Main {

    public static void main(String[] args) throws IOException {
//        Read r = new Read();
//        byte[] bytes = r.getByteArray(args[0]);

        int n = Integer.parseInt(args[1]);
        byte[] buffer = new byte[n*512];
        Map<ByteArr,Integer> frequences = new HashMap<>();
        FileInputStream fileRead = new FileInputStream((args[0]));
        int rc = fileRead.read(buffer);
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
            rc = fileRead.read(buffer);
        }

        Huffman coder = new Huffman();
        coder.createMap(frequences);
        Compress c = new Compress();
        c.compressFile(coder, n,args[0]);

//        BufferedWriter writer = new BufferedWriter( new FileWriter( "compressed.txt"));
//        writer.write( (char)255);
//        writer.close();
 //       System.out.println(Byte.toString((byte) 5));

//        try (FileOutputStream fos = new FileOutputStream("output.pdf")) {
//            fos.write(bytes);
//        }



    }
}