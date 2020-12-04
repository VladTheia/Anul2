import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.StringTokenizer;

public class FileWordCounter {
    public static void main(String[] args) throws IOException {
        int count = 0;
        FileReader in = new FileReader(new File("D:\\Facultate\\Anul 2\\Semestrul 1\\POO\\Lab13\\Arhiva 13\\test02.in"));
        StreamTokenizer str = new StreamTokenizer(in);
        while (str.nextToken()!=StreamTokenizer.TT_EOF) {
            if (str.ttype == StreamTokenizer.TT_WORD)
                count++;
        }
        System.out.println(count);
    }
}
