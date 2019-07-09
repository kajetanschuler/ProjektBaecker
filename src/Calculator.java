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
     * Function for calculating the confidence interval with 95% confidence level
     *
     */

    public void calculateConfidenceInterval (int anzahl, double dGewicht, double sDeviation){

        double low;
        double high;
        double difference;
        final double confidencecoefficient = 1.96; //95% confidence intervall -> 1.96 confidence coefficient

        //Calculating the lower bound
        low = (anzahl * dGewicht) - (confidencecoefficient * Math.sqrt(anzahl) * sDeviation);
        double lowgerundet = Math.round(low * 100.0) / 100.0;

        //Calculating the upper bound
        high = (anzahl * dGewicht) + (confidencecoefficient * Math.sqrt(anzahl) * sDeviation);
        double highgerundet = Math.round(high * 100.0) / 100.0;

        //Todo: High-Low -> Differenz mit Mittelwert vergleichen -> Größer als Mittelwert

        difference = high - low;

        if (difference > dGewicht){
            setLabelText("Ungenaue Messung!",3);
        }


        //setLabelText("Konfidenzintervall zwischen " + lowgerundet + " und " + highgerundet,3);

    }

}



