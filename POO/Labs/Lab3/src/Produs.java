public class Produs {
    String nume;
    double pret;
    int cantitate;

    public Produs(String nume, double pret, int cantitate) {
        this.nume = nume;
        this. pret = pret;
        this.cantitate = cantitate;
    }

    public String toString() {
        return "Produs " + nume + " " + pret + " " + cantitate;
    }

    public double getTotalProdus() {
        return cantitate * pret;
    }
}
