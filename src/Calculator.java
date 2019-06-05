import javafx.application.Platform;

public class Calculator {
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



    private void setLabelText(String text, int labelId) {

        // Update FX Elements through this code snippet
        Platform.runLater(new Runnable() {
            public void run() {
                Main.displayManager.setLabelText(text, labelId);
            }
        });
    }
}



