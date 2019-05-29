import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DisplayManager {
    private final GridPane gridPane = new GridPane();
    private Stage primaryStage;
    private Label status;
    private Label gewicht;
    private Label anzahl;
    private Timeline timeline;
    private ColumnConstraints cC;

    // Constructor for the display Manager - sets up windows
    public DisplayManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeWindows();

    }

    /**
     *
     */
    // Make GridLayout, Labels and Constraints
    private void initializeWindows () {

        // initialize GridPane
        gridPane.setGridLinesVisible(false);

        // Coloumn Constraints for correct width
        cC = new ColumnConstraints();
        cC.setPercentWidth(33);
        //cC.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().addAll(cC, cC, cC);

        status   = new Label("Warte auf NFC-Tag");

        // create animation for NFC Label
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
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Set labels
        gewicht = new Label("Hier kommt später das Gewicht rein");
        anzahl = new Label("Hier kommt Anzahl rein");

        // Prepare and Show Stage
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());
        primaryStage.setTitle("Projekt Bäcker");
        primaryStage.setFullScreen(true);
        primaryStage.show();

        // Todo: Add more labels
        GridPane liveData = new GridPane();
        cC.setPercentWidth(100);
        liveData.getColumnConstraints().addAll(cC);
        liveData.getChildren().addAll(status);

        timeline.play();
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
                pauseTimeline();
                // Todo: Timer für Reset des Labels
                break;

            case 1:
                gewicht.setText(text);
                break;
                // Todo: Set Animation with regard to given Text

            case 2:
                anzahl.setText(text);
                break;

        }

    }

    // Todo: Add function that adds GridPane live Data for reset and initialization

    /**
     *
     */
    private void pauseTimeline() {
        timeline.pause();
    }


}
