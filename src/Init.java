import java.util.ArrayList;

public class Init {
    public static ArrayList<Artikel> artikelData;

    public Init() {
        artikelData = new ArrayList<>();
        initializeArticleData();
    }

    // Function to initialize predefined article data
    private void initializeArticleData() {
        artikelData.add(new Artikel("Brezel", 135.59,2.16));
        artikelData.add(new Artikel("Streusel", 135.3));
        artikelData.add(new Artikel("Donut", 50));
    }

    public ArrayList<Artikel> getArtikelData() {
        return artikelData;
    }
}

