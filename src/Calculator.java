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
        double dgewicht;
        double retouregewicht;

        try {
            for (int i = 0; i < Main.artikelData.getArtikelData().size(); i++) {
                artikel = Main.artikelData.getArtikelData().get(i).getArtikel();
                dgewicht = Main.artikelData.getArtikelData().get(i).getdGewicht();

                if (artikel.equals(retoure.getArtikel())) {
                    retouregewicht = retoure.getGewicht();
                    System.out.println(retouregewicht);

                    double anzahl = Math.round(retouregewicht / dgewicht);
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



