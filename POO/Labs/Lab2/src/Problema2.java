import java.util.StringTokenizer;

public class Problema2 {
    static int nrAparitii(String s1, String s2) {
        int aparitii = 0;

        StringTokenizer str = new StringTokenizer(s1, ":,.-? \n");

        while (str.hasMoreTokens()) {
            if (s2.equals(str.nextToken()))
                aparitii++;
        }

        return aparitii;
    }

    public static void main(String args[]) {
        String s1 = "sir1 si sir2 sunt 2 siruri";
        String s2 = "sir2";
        System.out.println(nrAparitii(s1,s2));
    }
}
