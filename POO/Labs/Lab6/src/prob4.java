import java.io.*;
import java.util.Scanner;
import java.util.Vector;

class Filter implements FileFilter {
    Vector files;
    Vector words;

    public Filter() {
        files = new Vector();
        words = new Vector();
    }

    public boolean accept(File pathname) {
        String fileName = pathname.getName();
        return fileName.endsWith(".in");
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("D:\\Facultate\\Anul 2\\Semestrul 1\\POO\\Lab6\\Arhiva6\\extension.in");
        Scanner sc = new Scanner(file);
        Filter filter = new Filter();

        while(sc.hasNextLine()) {
            filter.files.add(sc.nextLine());
        }

        file = new File("D:\\Facultate\\Anul 2\\Semestrul 1\\POO\\Lab6\\Arhiva6\\words.in");
        sc = new Scanner(file);

        while(sc.hasNextLine()) {
            filter.words.add(sc.nextLine());
        }
    }
}