import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DisplayManager {
    private GridPane gridPane;
    private Stage primaryStage;
    private LabelHelper status;
    private LabelHelper gewicht;
    private LabelHelper anzahl;
    private Timeline timeline;
    private ColumnConstraints cC;
    private Scene scene;

    // Constructor for the display Manager - sets up windows
    public DisplayManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeWindows();

    }

    public void clearPane() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initializeWindows();
            }
        });
    }

    /**
     *
     */
    // Make GridLayout, Labels and Constraints
    public void initializeWindows () {

        // initialize GridPane and Scene
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(false);
        scene = new Scene(gridPane);

        // Coloumn Constraints for correct width
        cC = new ColumnConstraints();
        cC.setPercentWidth(33);
        //cC.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().addAll(cC, cC, cC);



/*        // create animation for NFC Label
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new EventHandler() {
                    @Override public void handle(Event event) {
                        String statusText = status.getText();
                        status.setText(
                                ("Warte auf NFC-Tag . . .".equals(statusText))
                                        ? "Warte auf NFC-Tag ."
                                        : statusText + " ."
                        );
                    }
                }),
                new KeyFrame(Duration.millis(350))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);*/

        // Set labels
        status = new LabelHelper("Warte auf NFC-Tag");
        gewicht = new LabelHelper("");
        anzahl = new LabelHelper("");

        // Set Animation for status Label
        status.animateLabelText("Warte auf NFC-Tag", "Warte auf NFC-Tag . . .");

        // Prepare and Show Stage
        //Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());
        primaryStage.setTitle("Projekt Bäcker");
        //primaryStage.setFullScreen(true);
        primaryStage.show();

        GridPane liveData = new GridPane();
        cC.setPercentWidth(100);
        liveData.getColumnConstraints().addAll(cC);
        liveData.getChildren().add(status);

        gridPane.add(liveData, 1, 0);

        liveData.add(gewicht, 0, 1);
        liveData.add(anzahl, 0, 2);

    }

    /**
     * Set Text in Label
     * @param text
     * @param labelId
     */
    public void setLabelText (String text, int labelId) {
        switch (labelId) {
            case 0:
                status.setText(text);
                status.pauseTimeline();
                // Todo: Timer für Reset des Labels
                break;

            case 1:
                gewicht.setText(text);
                gewicht.pauseTimeline();
                break;
                // Todo: Set Animation with regard to given Text

            case 2:
                anzahl.setText(text);
                break;

        }

    }

    public void animateLabel (String initialText, String endText, int labelId) {
        switch(labelId) {
            case 0:
                status.animateLabelText(initialText, endText);
                break;

            case 1:
                gewicht.animateLabelText(initialText, endText);
                break;

            case 2:
                anzahl.animateLabelText(initialText, endText);
                break;

        }
    }

    // Todo: Add function that adds GridPane live Data for reset and initialization

    public void resetNFCLabel() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gewicht.setText("");
                gewicht.pauseTimeline();
                anzahl.setText("");
                status.animateLabelText("Warte auf NFC-Tag", "Warte auf NFC-Tag . . .");
            }
        });


    }

    public void labelReset() {
        resetNFCLabel();
    }

    public LabelHelper getStatus() {
        return status;
    }
}
