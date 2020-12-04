import com.sun.xml.internal.ws.api.pipe.ServerTubeAssemblerContext;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

class Catalog extends TreeSet<Catalog.Student> {
    class Student implements Comparable{
        String name;
        double media;
        int clazz;

        public Student(String name, double media, int clazz) {
            this.name = name;
            this.media = media;
            this.clazz = clazz;
        }

        public int compareTo(Object o) {
            Student stud = (Student) o;
            double dif = media - stud.media;
            if (dif == 0)
                return name.compareTo(((Student) o).name);
            if (Catalog.this.c == null) {
                if (dif > 0)
                    return 1;
                return -1;
            } else {
                if (dif < 0)
                    return 1;
                return -1;
            }
        }

        public String toString() {
            return name + " " + media + " " + clazz;
        }
    }
    Comparator c;

    public Catalog(Comparator comparator) {
        c = comparator;
    }

    public Catalog() {
        c = null;
    }

    public void addStudent(String name, double media, int clazz) {
        Student stud = new Student(name, media, clazz);
        add(stud);
    }

    public Student getStudent(String name) {
        Iterator<Student> it = this.iterator();
        while (it.hasNext()) {
            Student stud = it.next();
            if (stud.name == name)
                return stud;
        }
        return null;
    }

    public void removeStudent(String name) {
        Iterator<Student> it = this.iterator();
        while (it.hasNext()) {
            Student stud = it.next();
            if (stud.name.equals(name)) {
                remove(stud);
                break;
            }
        }
    }

    public Catalog byClass(int clazz) {
        Catalog catalog = new Catalog();
        for (Student stud : this) {
            if (stud.clazz == clazz)
                catalog.add(stud);
        }
        return catalog;
    }

    public String toString() {
        String result = "\n";
        for (Student stud : this) {
            result += stud + "\n";
        }
        return result;
    }
}

class Task2 {

    public static void main(String args[]) {
        Catalog catalog = new Catalog();
       catalog.addStudent("Alexandru", 7, 324);
        catalog.addStudent("Ioana", 5, 321);
        catalog.addStudent("Maria", 10, 322);
        catalog.addStudent("Ionut", 6.2, 323);
        catalog.addStudent("Diana", 7, 322);

        Catalog catalog2 = new Catalog(new Comparator<Catalog.Student>() {
            @Override
            public int compare(Catalog.Student o1, Catalog.Student o2) {
                // TODO: Sort by average (descending) and by name (ascending).
                Catalog.Student s1 = (Catalog.Student) o1;
                Catalog.Student s2 = (Catalog.Student) o2;
                double dif = o2.media - o1.media;
                if (dif == 0)
                    return o1.name.compareTo(o2.name);
                if (dif > 0)
                    return 1;
                return -1;
            }
        });
        catalog2.addAll(catalog);

        System.out.println("Verificam...");
        System.out.println("Continutul colectiei: " + catalog);
        System.out.println("Continutul colectiei: " + catalog2);

        Catalog.Student last = null;
        for (Catalog.Student o : catalog) {
            if (last == null) {
                last = o;
                continue;
            }
            int r = last.media != o.media ? new Double(last.media).compareTo(o.media) : last.name.compareTo(o.name);
            if (r != last.compareTo(o)) {
                System.err.println("Catalog.Student.compareTo a fost implementata gresit.");
            }
        }

        if (catalog.size() != 5) {
            System.err.println("Catalog.size() a fost implementata gresit.");
        }

        catalog.removeStudent("Ionut");
        if (catalog.size() != 4) {
            System.err.println("Catalog.remove()1 a fost implementata gresit.");
        }
        catalog.removeStudent("");
        if (catalog.size() != 4) {
            System.err.println("Catalog.remove()2 a fost implementata gresit.");
        }

        if (catalog.byClass(322).size() != 2) {
            System.err.println("Catalog.byClass() a fost implementata gresit.");
        }

        catalog.removeStudent("Maria");
        if (catalog.byClass(322).size() != 1) {
            System.err.println("Catalog.remove()3 a fost implementata gresit.");
        }

        if (catalog.getStudent("Maria") != null) {
            System.err.println("Catalog.getStudent() a fost implementata gresit.");
        }

        if ((catalog.getStudent("Alexandru") == null) || (catalog.getStudent("Alexandru").media != 7)) {
            System.err.println("Catalog.getStudent() a fost implementata gresit.");
        }
    }
}