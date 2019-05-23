public class Retoure {
    private int id;
    private int filiale;
    private String artikel;
    private float gewicht;
    private float durchschnittsgewicht;

    public Retoure(int id, int filiale, String artikel, float gewicht) {
        this.id = id;
        this.filiale = filiale;
        this.artikel = artikel;
        this.gewicht = gewicht;
    }

    private int berechneAnzahl (float durchschnittsgewicht, float gewicht) {
        return 0;
    }
}
