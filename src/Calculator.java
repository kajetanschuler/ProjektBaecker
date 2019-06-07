import javafx.application.Platform;

public class Calculator extends SerialController {
    private Retoure retoure;

    public Calculator(Retoure retoure) {
        this.retoure = retoure;
    }

    /**
     * Function for calculating the amount of baked goods
     */

    public void calculateAmount() {
        String artikel;
        double dGewicht;
        double retoureGewicht;
        double sDeviation;

        try {
            // Comparison of returned articles with predefined article data
            for (int i = 0; i < Main.artikelData.getArtikelData().size(); i++) {
                artikel = Main.artikelData.getArtikelData().get(i).getArtikel();
                dGewicht = Main.artikelData.getArtikelData().get(i).getdGewicht();
                sDeviation = Main.artikelData.getArtikelData().get(i).getsDeviation();

                if (artikel.equals(retoure.getArtikel())) {
                    retoureGewicht = retoure.getGewicht();
                    double anzahl = Math.round(retoureGewicht / dGewicht);
                    int anzahlgerundet = (int) Math.round(anzahl);

                    setLabelText(Integer.toString(anzahlgerundet) + " " + artikel,2);
                }
            }

        } catch (Exception e) {
            // Show error when amount cannot be calculated
            setLabelText("Amount of baked goods could not be calculated.",2);
        }

    }
}



