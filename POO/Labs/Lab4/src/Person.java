import java.util.Enumeration;
import java.util.Vector;

public class Person {
    String name;
    String address;

    public Person() {
        this("Nume","Adresa");
    }

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString() {
        return "Nume: " + name + "\nAdresa: " + address;
    }
}

class Student extends Person {
    Vector courses;
    Vector grades;

    public Student(String name, String address) {
        super(name, address);
        courses = new Vector();
        grades = new Vector();
    }

    void addCourseGrade(String course, int grade) {
        courses.add(course);
        grades.add(grade);
    }

    void printGrades() {
        Enumeration enumeration = grades.elements();
        String result = "{";
        while(enumeration.hasMoreElements()) {
            result += enumeration.nextElement() + ", ";
        }
        result += "}";
        System.out.println(result);
    }

    double getAverageGrade() {
        double total = 0;
        double nr = 0;
        Enumeration enumeration = grades.elements();
        while(enumeration.hasMoreElements()) {
            total += (double) enumeration.nextElement();
            nr++;
        }
        return total / nr;
    }
}

class Teacher extends Person {
    Vector courses;

    public Teacher(String name, String address) {
        super(name, address);
        courses = new Vector();
    }

    boolean addCourse(String course) {
        if (courses.contains(course))
            return false;
        courses.add(course);
        return true;
    }

    boolean removeCourse(String course) {
        if (courses.contains(course))
            return false;
        courses.remove(course);
        return true;
    }
}

class Test4 {
    public static void main(String args[]) {
        Person student, teacher, person;
        student = new Student("Popescu Ion", "Bucuresti");
        teacher = new Teacher("Ionescu Gigel", "Bucuresti");
        person = new Person("Maria", "Iasi");
        assert (person.getName().equals("Maria")) : "Metoda getName din clasa Person nu este implementata corect";
        assert (((Teacher) teacher).addCourse("Programare")) : "Metoda addCourse din clasa Teacher nu este " +
                "implementata corect";
        assert (((Teacher) teacher).addCourse("Algoritmica")) : "Metoda addCourse din clasa Teacher nu este " +
                "implementata corect";
        assert (((Teacher) teacher).addCourse("Matematica")) : "Metoda addCourse din clasa Teacher nu este " +
                "implementata corect";
        assert (!((Teacher) teacher).addCourse("Programare")) : "Metoda addCourse din clasa Teacher nu este " +
                "implementata corect";
        assert (((Teacher) teacher).removeCourse("Programare")) : "Metoda addCourse din clasa Teacher nu este " +
                "implementata corect";
        assert (!((Teacher) teacher).addCourse("Programare")) : "Metoda addCourse din clasa Teacher nu este " +
                "implementata corect";
        ((Student) student).addCourseGrade("Programare", 10);
        ((Student) student).addCourseGrade("Algoritmica", 9);
        ((Student) student).addCourseGrade("Matematica", 8);
        assert (Math.abs(((Student) student).getAverageGrade() - 9.00) <= 0.001) : "Metoda getAverageGrade din clasa " +
                "Student nu a fost implementat corect";
        ((Student) student).printGrades();
        //Ce metoda toString se va apela? Din ce clasa?
        System.out.println(student);
        System.out.println(person);
        System.out.println("Felicitari! Problema a fost rezolvata corect!");
    }
}