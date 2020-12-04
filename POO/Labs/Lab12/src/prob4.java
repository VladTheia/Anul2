import java.time.temporal.Temporal;

public class prob4 {
}

interface Strategy {
    float calcul(int aniVechime, float salariu);
}

class TwentyStrategy implements Strategy{
    public float calcul(int aniVechime, float salariu) {
        return aniVechime  * salariu / 20;
    }
}

class ThirtyStrategy implements Strategy{
    public float calcul(int aniVechime, float salariu) {
        return aniVechime / 30 * salariu;
    }
}

class FortyStrategy implements Strategy{
    public float calcul(int aniVechime, float salariu) {
        return aniVechime / 40 * salariu;
    }
}

class Pensionar {
    int aniVechime;
    float salariu;
    Strategy strategy;

    public Pensionar(int aniVechime, float salariu) {
        this.aniVechime = aniVechime;
        this.salariu = salariu;
    }

    float getPensie() {
        if (aniVechime < 30) {
            TwentyStrategy strg = new TwentyStrategy();
            return strg.calcul(aniVechime, salariu);
        }
        if (aniVechime < 40) {
            ThirtyStrategy strg = new ThirtyStrategy();
            return strg.calcul(aniVechime, salariu);
        }
        if (aniVechime < 50) {
            FortyStrategy strg = new FortyStrategy();
            return strg.calcul(aniVechime, salariu);
        }
        return 0;
    }

    public static void main(String[] args) {
        Pensionar p1 = new Pensionar(25, 2000);
        Pensionar p2 = new Pensionar(35, 3000);
        Pensionar p3 = new Pensionar(45, 5000);

        System.out.println("Salariul 1: " + p1.getPensie());
        System.out.println("Salariul 2: " + p2.getPensie());
        System.out.println("Salariul 3: " + p3.getPensie());
    }
}