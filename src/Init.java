import java.util.ArrayList;

public class Init {
    ArrayList<Kiste> dummyKisten = new ArrayList<>();
    ArrayList<Artikel> dummyArtikel = new ArrayList<>();

    public Init(ArrayList<Kiste> dummyKisten, ArrayList<Artikel> dummyArtikel) {
        this.dummyKisten = dummyKisten;
        this.dummyArtikel = dummyArtikel;
        makeDummyKisten();
        makeDummyArtikel();
    }

    private void makeDummyKisten () {
        dummyKisten.add(new Kiste(15, 34561234, "Bretzel"));
        dummyKisten.add(new Kiste(12, 34948234, "Streusel"));
        dummyKisten.add(new Kiste(8, 34518334, "Dinkelbrot"));
    }

    private void makeDummyArtikel () {
        dummyArtikel.add(new Artikel("Bretzel", 94.5f));
        dummyArtikel.add(new Artikel("Streusel", 176.9f));
        dummyArtikel.add(new Artikel("Dinkelbrot", 498.7f));
    }

    public Kiste getDummyKisten(int i) {
        return dummyKisten.get(i);
    }

    public Artikel getDummyArtikel(int i) {
        return dummyArtikel.get(i);
    }

    public ArrayList<Kiste> getDummyKistenArray() {
        return dummyKisten;
    }

    public ArrayList<Artikel> getDummyArtikelArray() {
        return dummyArtikel;
    }
}
