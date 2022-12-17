import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DeCompressed {
    public void decompressFile(String path) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("compressed.ser");
        ArrayList<Object> objectsList = new ArrayList<>();
        boolean cont = true;
        while (cont) {
            try (ObjectInputStream input = new ObjectInputStream(fis)) {
                Object obj = input.readObject();
                if (obj != null) {
                    objectsList.add(obj);
                } else {
                    cont = false;
                }
            } catch (Exception e) {
                System.out.println("not find file");
            }
        }
        Map<byte[], Byte> keyMap = (Map<byte[], Byte>) objectsList.get(0);
        LinkedList<Byte> coded = (LinkedList<Byte>) objectsList.get(1);
        for (byte b:coded) {

        }



//        try (FileOutputStream fos = new FileOutputStream("output.pdf")) {
//            fos.write(bytes);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
