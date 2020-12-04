public class Problema1 {
    public static void main(String args[]) {
        String s1 = "si";
        String s = new String("sir1 si cu sir2 fac un sir3");

        int pos, aparitii = 0;
        pos = s.indexOf(s1, 0);

        while (pos != -1){
            aparitii++;
            pos = s.indexOf(s1, pos + s1.length());
        }

        System.out.println(aparitii);
    }
}
