import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
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
    private Button closeButton;


    // Constructor for the display Manager - sets up windows
    public DisplayManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeWindows();

    }

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

        // Set Button
        closeButton = new Button("Beenden");

        // Set Animation for status Label
        status.animateLabelText("Warte auf NFC-Tag", "Warte auf NFC-Tag . . .");

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

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
        gridPane.add(closeButton, 2,0);

        liveData.add(gewicht, 0, 1);
        liveData.add(anzahl, 0, 2);


        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.exit(0);
            }
        });

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
                break;

            case 1:
                gewicht.setText(text);
                gewicht.pauseTimeline();
                break;

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

    public void labelReset() {
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

    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public LabelHelper getStatus() {
        return status;
    }
}
