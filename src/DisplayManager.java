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


    public DisplayManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeWindows();

    }

    private void initializeWindows () {

        // initialize GridPane
        gridPane.setGridLinesVisible(false);

        // Coloumn Constraints for correct width
        cC = new ColumnConstraints();
        cC.setPercentWidth(33);
        //cC.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().addAll(cC, cC, cC);

        status   = new Label("Warte auf NFC-Tag");
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


        // Prepare and Show Stage
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());
        primaryStage.setTitle("Projekt Bäcker");
        primaryStage.setFullScreen(true);
        primaryStage.show();

        GridPane liveData = new GridPane();
        cC.setPercentWidth(100);
        liveData.getColumnConstraints().addAll(cC);
        liveData.getChildren().addAll(status);

        timeline.play();
        gridPane.add(liveData, 1, 0);


        liveData.add(new Label(""), 0, 1);
        liveData.add(new Label(""), 0, 2);

    }

    public void setLabelText (String text, int labelId) {
        switch (labelId) {
            case 0:
                status.setText(text);
                pauseTimeline();
                break;

            case 1:
                gewicht.setText(text);
                break;

            case 2:
                anzahl.setText(text);
                break;

        }

    }

    private void pauseTimeline() {
        timeline.pause();
    }


}
