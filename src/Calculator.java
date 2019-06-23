import com.sun.media.sound.SoftTuning;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
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
                    double anzahl = retoureGewicht / dGewicht;
                    //7.3 -> Amount: 7; 7.6 -> Amount: 8
                    int anzahlgerundet = (int) Math.round(anzahl);

                    //Todo: Ggf. Anzeigetext ändern
                    setLabelText(Integer.toString(anzahlgerundet) + " " + artikel,2);


                    calculateConfidenceInterval(anzahlgerundet, dGewicht, sDeviation);

                }
            }

        } catch (Exception e) {
            // Show error when amount cannot be calculated
            setLabelText("Amount of baked goods could not be calculated.",2);
        }


    }

    /**
     * Function for calculating the confidence interval
     */

    public void calculateConfidenceInterval (int anzahl, double dGewicht, double sDeviation){

        double low;
        double high;

        low = (anzahl * dGewicht) - (1.96 * Math.sqrt(anzahl) * sDeviation);
        double lowgerundet = Math.round(low * 100.0) / 100.0;

        high = (anzahl * dGewicht) + (1.96 * Math.sqrt(anzahl) * sDeviation);
        double highgerundet = Math.round(high * 100.0) / 100.0;

        System.out.println("Der Konfidenzintervall liegt zwischen " + lowgerundet + " und " + highgerundet + ".");

        //TODO: Dynamische Änderung der Quantilberechnung / Bestimmung Z Wert
    }




}



