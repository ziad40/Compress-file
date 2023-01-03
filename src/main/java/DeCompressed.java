import org.apache.commons.lang3.SerializationUtils;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class DeCompressed {
    public void decompressFile(String path) throws IOException {
        File file = new File(path);
        FileInputStream fileRead = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileRead);

        byte[] buffer = new byte[Long.BYTES];
        int rc = bufferedInputStream.read(buffer);
        long fileSize = 0;
        if (rc != -1){
            ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);
            buf.put(buffer);
            buf.flip();//need flip
            fileSize = buf.getLong();
        }

        buffer = new byte[1];
        rc = bufferedInputStream.read(buffer);
        if (rc != -1){
            int len_header = 0;
            len_header += buffer[0];
            buffer = new byte[len_header];
        }
        rc = bufferedInputStream.read(buffer);
        if(rc != -1)
        {
            int len_header_file_data = 0;
            byte count = 0;
            for (int i=buffer.length-1; i >= 0; i--){
                int s = buffer[i];
                if (buffer[i] < 0){
                    s += 256;
                }
                if (count == 0){
                    len_header_file_data += s;
                }else{
                    len_header_file_data += s * 256*count;
                }
                count++;

            }
            buffer = new byte[len_header_file_data];
        }
        rc = bufferedInputStream.read(buffer);
        Node root = new Node(0,null,null);
        Huffman coder = new Huffman();
        if (rc != -1){
            root = SerializationUtils.deserialize(buffer);
            coder.createMap(root,1);
        }

        buffer = new byte[1024];
        rc = bufferedInputStream.read(buffer);
        String fileName = file.getName();
        int index = fileName.length()-1;
        while (fileName.charAt(index) != '.')index--;
        fileName = fileName.substring(0,index);
        FileOutputStream fileOut = new FileOutputStream("extracted."+fileName);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOut);
        int counter = 0;
        StringBuilder strToSearch = new StringBuilder();
        LinkedList<Byte> listOfBytes = new LinkedList<>();


        for (Map.Entry <ByteArr,ByteArr> e : coder.keyMap.entrySet()) {

            byte[] y = new byte[]{-114};
            ByteArr x = new ByteArr(y);
            if (e.getValue().equals(x)){
                System.out.println("map");
                System.out.println(e.getKey());
                break;
            }

        }

        while (rc != -1){
            for (int i = 0; i< rc ;i++){
                StringBuilder str = new StringBuilder();

                str.append(Integer.toBinaryString(buffer[i] & 0xff));
                while (str.length() < 8){
                    str.insert(0,'0');
                }

                for(int j = 0;j < str.length();j++){
                    strToSearch.append(str.substring(j,j+1));
                    counter++;
                    listOfBytes.push((byte) Integer.parseInt(strToSearch.toString(),2));
                    ByteArr b = new ByteArr(new byte[listOfBytes.size()+1]);
                    for (int k = 0; k < listOfBytes.size(); k++)
                        b.array[k] = listOfBytes.get(k);
                    b.array[b.array.length-1] = (byte) counter;

                    if (coder.keyMap.containsKey(b)){
                        System.out.println(Arrays.toString(coder.keyMap.get(b).array));
                        bufferedOutputStream.write(coder.keyMap.get(b).array);
                        counter = 0;
                        listOfBytes.clear();
                        strToSearch = new StringBuilder();
                        fileSize--;
                        if (fileSize == 0)break;
                    }
                    if (strToSearch.length() == 8){
                        strToSearch = new StringBuilder();
                    }else{
                        listOfBytes.poll();
                    }

                }
            }
            rc = bufferedInputStream.read(buffer);
        }
        bufferedOutputStream.close();

    }
}
