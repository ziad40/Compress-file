import org.apache.commons.lang3.SerializationUtils;
import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

public class Compress {
    public void compressFile(Huffman coder, int n,String path) throws IOException {
        long bytes = Files.size(Path.of(path));
        File file = new File(path);
        FileOutputStream fileOut = new FileOutputStream("compressed."+ n +"."+file.getName()+".hc");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOut);
        byte[] data = SerializationUtils.serialize(coder.Root);
        BigInteger l = BigInteger.valueOf(data.length);
        byte[] Len = l.toByteArray();

        ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);
        buf.putLong(bytes);
        bufferedOutputStream.write(buf.array());
        bufferedOutputStream.write((byte)Len.length);
        bufferedOutputStream.write(Len);
        bufferedOutputStream.write(data);
        byte[] buffer = new byte[n*512];
        FileInputStream fileRead = new FileInputStream(path);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileRead);
        int rc = bufferedInputStream.read(buffer);
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
                    bytes--;

                    index = 0;
                    arr = new ByteArr(new byte[n]);
                }
            }
            while (str.length() >= 8) {
                byte toWritenByte = 0;
                byte pow = 0;
                if (str.charAt(0) == '1')
                    toWritenByte |= 0b10000000;
                for (int k = 7; k > 0; k--) {
                    if (str.charAt(k) == '1')
                        toWritenByte |= (int) Math.pow(2, pow);
                    pow++;
                }
                bufferedOutputStream.write(toWritenByte);
                str.delete(0, 8);
            }
            if (bytes == 0){
                while (str.length() < 8){
                    str.append('0');
                }
                bufferedOutputStream.write((byte)(Integer.parseInt(str.toString(),2) & 0xff));
            }





            rc = bufferedInputStream.read(buffer);
            str = new StringBuilder();
        }
        bufferedOutputStream.close();
        System.out.println("file is compressed");


    }
}
