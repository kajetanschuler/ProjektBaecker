import javafx.application.Platform;

public class SerialController {

    public void setLabelText(String text, int labelId) {
        // Update FX Elements through this code snippet
        Platform.runLater(new Runnable() {
            public void run() {
                Main.displayManager.setLabelText(text, labelId);
            }
        });
    }

    public void animateLabel(String initialText, String endText, int labelId) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main.displayManager.animateLabel(initialText, endText, labelId);
            }
        });
    }
}
