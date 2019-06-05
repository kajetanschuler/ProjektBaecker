import java.util.ArrayList;

public class Init {
    public static ArrayList<Artikel> artikelData;

    public Init() {
        artikelData = new ArrayList<>();
        initializeArticleData();
    }

    private void initializeArticleData() {
        artikelData.add(new Artikel("Brezel", 135.65));
        artikelData.add(new Artikel("Streusel", 135.3));
        artikelData.add(new Artikel("Donut", 38.4));
    }

    public ArrayList<Artikel> getArtikelData() {
        return artikelData;
    }
}

