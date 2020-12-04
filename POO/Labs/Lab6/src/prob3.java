import java.util.Collections;
import java.util.Vector;

interface Persoana extends Comparable {
    public double calculMedieGenerala();
    public String getNume();
    public void setNume(String nume);
    public void addMedie(double medie);
}

class Student implements Persoana {
    private String nume;
    private Vector medii;

    public Student() {
        medii = new Vector();
    }

    public double calculMedieGenerala() {
        double total = 0;
        for (int i = 0; i < medii.size(); i++)
            total += (double) medii.get(i);
        return total / medii.size();
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void addMedie(double medie) {
        medii.add(medie);
    }

    public int compareTo(Object o) {
        Student s = (Student) o;
        int compare = nume.compareTo(s.nume);
        if (compare != 0)
            return compare;
        double med = s.calculMedieGenerala() - calculMedieGenerala();
        return (int) med;
    }

    public String toString() {
        return getNume() + " are media " + calculMedieGenerala() + '\n';
    }

    public static void main(String[] args) {
        Student s1 = new Student();
        s1.setNume("Andrei");
        s1.addMedie(10);
        s1.addMedie(8);
        Student s2 = new Student();
        s2.setNume("Andrei");
        s2.addMedie(10);
        s2.addMedie(10);
        Student s3 = new Student();
        s3.setNume("Vlad");
        s3.addMedie(8);
        s3.addMedie(8);
        Student s4 = new Student();
        s4.setNume("Mihai");
        s4.addMedie(8);
        s4.addMedie(6);

        Vector studenti = new Vector();
        studenti.add(s1);
        studenti.add(s2);
        studenti.add(s3);
        studenti.add(s4);
        Collections.sort(studenti);
        System.out.println(studenti);
    }
}