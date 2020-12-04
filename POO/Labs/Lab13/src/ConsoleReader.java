import java.io.*;

public class ConsoleReader {
    public static void main(String[] args) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\Facultate\\Anul 2\\Semestrul 1\\POO\\Lab13\\Arhiva 13\\output.out"));
            BufferedReader br = null;
            Reader r = new InputStreamReader(System.in);
            br = new BufferedReader(r);
            String str = null;
            do{
                str = br.readLine();
                bw.write(str + '\n');
            } while (!str.equalsIgnoreCase("exit"));
            bw.close();
            br.close();
            r.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
