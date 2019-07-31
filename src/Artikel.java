// Class to represent Article Details
public class Artikel {
    private String artikel;
    private double dGewicht;
    private double sDeviation;

    public Artikel(String artikel, double dGewicht) {
        this.artikel = artikel;
        this.dGewicht = dGewicht;
    }

    public Artikel(String artikel, double dGewicht, double sDeviation) {
        this.artikel = artikel;
        this.dGewicht = dGewicht;
        this.sDeviation = sDeviation;
    }

    public String getArtikel() {
        return artikel;
    }

    public void setArtikel(String artikel) {
        this.artikel = artikel;
    }

    public double getdGewicht() {
        return dGewicht;
    }

    public void setdGewicht(double dGewicht) {
        this.dGewicht = dGewicht;
    }

    public double getsDeviation() {
        return sDeviation;
    }

    public void setsDeviation(double sDeviation) {
        this.sDeviation = sDeviation;
    }
}
