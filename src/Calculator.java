import javafx.application.Platform;

public class Calculator {
    private Retoure retoure;

    public Calculator(Retoure retoure) {
        this.retoure = retoure;
    }

    public void calculateAmount() {
        String artikel;
        for (int i = 0; i < Main.artikelData.getArtikelData().size(); i++) {
            artikel = Main.artikelData.getArtikelData().get(i).getArtikel();
            System.out.println(artikel);
            if (artikel.equals(retoure.getArtikel())) {

            }
        }
        double anzahl = (retoure.getGewicht() / retoure.getDurchschnittsgewicht());


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



