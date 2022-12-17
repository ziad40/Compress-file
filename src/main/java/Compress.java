import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Compress {
    public void compressFile(Huffman coder, int n,String path) throws IOException {
        byte[] data = SerializationUtils.serialize(user);
        coder.Root
        byte[] buffer = new byte[n*512];
        FileInputStream fileRead = new FileInputStream(path);
        int rc = fileRead.read(buffer);
        StringBuilder str = new StringBuilder();
        while(rc != -1)
        {
            ByteArr arr = new ByteArr(new byte[n]);
            int index = 0;
            for (int j= 0; j < rc;j++){
                arr.array[index] = buffer[j];
                index++;
                if (index == n){
                    ByteArr array = coder.reverseKeyMap.get(arr);   // array -> get codeword
                    StringBuilder s = new StringBuilder();
                    int lenOfCode = array.array.length;
                    for (int i=0 ;i < lenOfCode-1;i++) {
                        if (array.array[i] < 0)
                            s.append(Integer.toBinaryString(array.array[i] + 256));
                        else
                            s.append(Integer.toBinaryString(array.array[i]));
                    }
                    while (s.length() < array.array[lenOfCode-1]){
                        s.insert(0,'0');
                    }
                    str.append(s);

                    index = 0;
                    arr = new ByteArr(new byte[n]);
                }
            }
            byte[] compressedBytes = new byte[str.length()/8+1];
            int iter=0;
            int len = str.length();
            for (int i=0 ;i < len ;i=i+8){
                byte b = 0;
                byte pow = 0;
                if (str.charAt(i) == '1')
                    b |= 0b10000000;
                for (int j = Math.min(i+8-1,len-1); j > i ;j--){
                    if (str.charAt(j) == '1')
                        b |= (int)Math.pow(2,pow);
                    pow++;
                }
                // write b
                compressedBytes[iter] = b;
                iter++;
            }
            rc = fileRead.read(buffer);
            str = new StringBuilder();
        }



//        try {
//            FileOutputStream fileOut = new FileOutputStream("compressed.ser");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(coder.Root);
//            System.out.println("write map .");
//            LinkedList<Byte> compressedBytes = new LinkedList<>();
//            for (ByteArr b : BYTES){
//                ByteArr array = coder.reverseKeyMap.get(b);   // array -> get codeword
//                StringBuilder s = new StringBuilder();
//                int lenOfCode = array.array.length;
//                for (int i=0 ;i < lenOfCode-1;i++) {
//                    if (array.array[i] < 0)
//                        s.append(Integer.toBinaryString(array.array[i] + 256));
//                    else
//                        s.append(Integer.toBinaryString(array.array[i]));
//                }
//                while (s.length() < array.array[lenOfCode-1]){
//                    s.insert(0,'0');
//                }
//                str.append(s);
//                while (str.length() >= 8){
//                    byte toWritenByte = 0;
//                    byte pow = 0;
//                    if (str.charAt(0) == '1')
//                        toWritenByte |= 0b10000000;
//                    for (int k = 7; k > 0 ;k--){
//                        if (str.charAt(k) == '1')
//                            toWritenByte |= (int)Math.pow(2,pow);
//                        pow++;
//                    }
//                    compressedBytes.add(toWritenByte);
//                    str.delete(0,8);
//                }
//            }
//            if (!str.isEmpty()){
//                byte toWritenByte = 0;
//                int pow = 8-str.length();
//                if (str.charAt(0) == '1')
//                    toWritenByte |= 0b10000000;
//                for (int k = str.length()-1; k > 0 ;k--){
//                    if (str.charAt(k) == '1')
//                        toWritenByte |= (int)Math.pow(2,pow);
//                    pow++;
//                }
//                compressedBytes.add(toWritenByte) ;
//            }
//            out.writeObject(compressedBytes);
//            out.close();
//            fileOut.close();
//        } catch (IOException i) {
//            i.printStackTrace();
//        }



//        byte[] compressedBytes = new byte[str.length()/8+1];
//        int index=0;
//        int len = str.length();
//        for (int i=0 ;i < len ;i=i+8){
//            byte b = 0;
//            byte pow = 0;
//            if (str.charAt(i) == '1')
//                b |= 0b10000000;
//            for (int j = Math.min(i+8-1,len-1); j > i ;j--){
//                if (str.charAt(j) == '1')
//                    b |= (int)Math.pow(2,pow);
//                pow++;
//            }
//            compressedBytes[index] = b;
//            index++;
//        }
        System.out.println("file is compressed");


    }
}
