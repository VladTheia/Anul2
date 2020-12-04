import java.util.*;

public class Problema4 {
    Vector reuniune(Vector vec1, Vector vec2) {
        if(vec1.equals(vec2))
            return vec1;

        Vector reVec = new Vector();
        reVec.addAll(vec1);
        Iterator it = vec2.iterator();
        while (it.hasNext()) {
            int elem = (int) it.next();
            if (!reVec.contains(elem)) {
                reVec.add(elem);
            }
        }
        Collections.sort(reVec);
        return reVec;
    }

    Vector intersectie(Vector vec1, Vector vec2) {
        if(vec1.equals(vec2))
            return vec1;

        Vector inVec = new Vector();
        Iterator it = vec2.iterator();
        while (it.hasNext()) {
            int elem = (int) it.next();
            if (vec1.contains(elem)) {
                inVec.add(elem);
            }
        }
        Collections.sort(inVec);
        return inVec;
    }

    Vector diferenta(Vector vec1, Vector vec2) {
        Vector difVec = new Vector();
        difVec.addAll(vec1);
        Iterator it = vec2.iterator();
        while (it.hasNext()) {
            int elem = (int) it.next();
            if (difVec.contains(elem)) {
                difVec.remove(difVec.indexOf(elem));
            }
        }
        return difVec;
    }

    public static void main(String args[]) {
        Vector vec1 = new Vector();
        vec1.add(1);
        vec1.add(2);
        vec1.add(8);
        Vector vec2 = new Vector();
        vec2.add(1);
        vec2.add(4);
        vec2.add(5);
        Problema4 p = new Problema4();
        Vector reVec = p.reuniune(vec1,vec2);
        Vector inVec = p.intersectie(vec1, vec2);
        Vector difVec = p.diferenta(vec1, vec2);
        System.out.println(reVec);
        System.out.println(inVec);
        System.out.println(difVec);
    }
}
