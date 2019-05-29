public class Retoure {
    private int filiale;
    private String artikel;
    private double gewicht;
    private double durchschnittsgewicht;

    public Retoure(int filiale, String artikel, double gewicht) {
        this.filiale = filiale;
        this.artikel = artikel;
        this.gewicht = gewicht;
    }

    public Retoure (int filiale, String artikel) {
        this.filiale = filiale;
        this.artikel = artikel;
    }

    public int getFiliale() {
        return filiale;
    }

    public String getArtikel() {
        return artikel;
    }

    public double getGewicht() {
        return gewicht;
    }

    public double getDurchschnittsgewicht() {
        return durchschnittsgewicht;
    }

    public void setFiliale(int filiale) {
        this.filiale = filiale;
    }

    public void setArtikel(String artikel) {
        this.artikel = artikel;
    }

    public void setGewicht(double gewicht) {
        this.gewicht = gewicht;
    }

    public void setDurchschnittsgewicht(double durchschnittsgewicht) {
        this.durchschnittsgewicht = durchschnittsgewicht;
    }
}
