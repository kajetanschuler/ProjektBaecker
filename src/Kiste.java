public class Kiste {
    private int filiale;
    private int id;
    private String artikel;

    public Kiste (int filiale, int id, String artikel) {
        this.artikel = artikel;
        this.filiale = filiale;
        this.id = id;
    }

    public int getFiliale() {
        return filiale;
    }

    public int getId() {
        return id;
    }

    public String getArtikel() {
        return artikel;
    }
}
