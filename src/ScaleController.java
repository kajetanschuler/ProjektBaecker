import javafx.application.Platform;

public class ScaleController {
    private Retoure retoure;

    public ScaleController(Retoure retoure) {
        this.retoure = retoure;
        setLabelText("Warte auf Gewicht...", 1);
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
