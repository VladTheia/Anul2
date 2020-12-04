import javax.security.auth.Subject;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class Prob3 {
    public static void main(String[] args) throws IOException {
        FileReader fr = null;
        LineNumberReader lineNumberReader= null;
        try {
            fr = new FileReader("D:\\Facultate\\Anul 2\\Semestrul 1\\POO\\Lab13\\Arhiva 13\\test01.in");
            lineNumberReader = new LineNumberReader(fr);
            String str;
            for (int i = 0; i < 100; i++) {
                while ((str=lineNumberReader.readLine()) != null) {
                    if (lineNumberReader.getLineNumber() % 2 != 0) {
                        System.out.println(lineNumberReader.getLineNumber());
                        System.out.println(str);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fr.close();
            lineNumberReader.close();
        }
    }
}
