import java.util.StringTokenizer;
import java.util.Vector;

public class Problema5 {
    String cenzurare(String text, String[] cuvinte) {
        Vector words = new Vector();
        for (int i = 0; i < cuvinte.length; i++)
            words.add(cuvinte[i]);

        String result = "";
        StringTokenizer st = new StringTokenizer(text);
        while (st.hasMoreTokens()) {
            String check = st.nextToken();
            StringBuffer word = new StringBuffer(check);
            if (words.contains(check)) {
                for (int i = 1; i < check.length() - 1; i++)
                    word.setCharAt(i, '*');
            }
            result += word + " ";
        }

        return result;
    }

    public static void main(String[] args) {
        String text = "Un terorist avea o bomba";
        String cuvinte[] = new String[2];
        cuvinte[0] = "terorist";
        cuvinte[1] = "bomba";
        Problema5 prb5 = new Problema5();
        String rezultat;
        rezultat = prb5.cenzurare(text, cuvinte);
        System.out.println(rezultat);
    }
}
