import javafx.application.Platform;

public class Calculator extends SerialController {
    private Retoure retoure;

    public Calculator(Retoure retoure) {
        this.retoure = retoure;
    }


    /**
     *
     */

    public void calculateAmount() {
        String artikel;
        double dgewicht;
        double retouregewicht;

        for (int i = 0; i < Main.artikelData.getArtikelData().size(); i++) {
            artikel = Main.artikelData.getArtikelData().get(i).getArtikel();
            dgewicht = Main.artikelData.getArtikelData().get(i).getdGewicht();

            System.out.println(artikel);
            System.out.println(dgewicht);

            if (artikel.equals(retoure.getArtikel())) {
                retouregewicht = retoure.getGewicht();
                System.out.println(retouregewicht);
                double anzahl = Math.round(retouregewicht / dgewicht);
                int anzahlgerundet = (int) Math.round(anzahl);

                setLabelText(Integer.toString(anzahlgerundet) + " " + artikel,2);
            }
        }
    }

}



