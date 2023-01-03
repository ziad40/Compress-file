import java.io.Serializable;
import java.util.Arrays;

public class ByteArr implements Serializable {
    public final byte[] array;
    public ByteArr(byte[] array) {
        this.array = array.clone();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteArr byteArray = (ByteArr) o;
        return Arrays.equals(array, byteArray.array);
    }
    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }
}

