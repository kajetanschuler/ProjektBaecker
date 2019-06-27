import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
    //private GridPane gridPane;
    private GridPane gridPane1;
    private GridPane gridPane2;
    private GridPane gridPane3;
    private Stage primaryStage;
    private LabelHelper status;
    private LabelHelper gewicht;
    private LabelHelper anzahl;
    private LabelHelper intervall;
    private Timeline timeline;
    //private ColumnConstraints cC;
    private ColumnConstraints cC1;
    private ColumnConstraints cC2;
    private ColumnConstraints cC3;
    private ColumnConstraints cC4;
    private Scene scene;
    private Button closeButton;


    // Constructor for the display Manager - sets up windows
    public DisplayManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeWindows();

    }

    // Make GridLayout, Labels and Constraints
    public void initializeWindows () {


        // initialize 1st GridPane and Scene

        gridPane1 = new GridPane();
        gridPane1.setGridLinesVisible(false);
        scene = new Scene(gridPane1);

        //cC = new ColumnConstraints();
        //cC.setPercentWidth(33);


        // initialize  2nd GridPane and Scene
        gridPane2 = new GridPane();
        gridPane2.setGridLinesVisible(false);
        //scene = new Scene(gridPane2);

        gridPane3 = new GridPane();
        gridPane3.setGridLinesVisible(false);



        // Coloumn Constraints for correct width
        cC1 = new ColumnConstraints();
        cC1.setPercentWidth(100);
        //cC.setHalignment(HPos.CENTER);

        cC2 = new ColumnConstraints();
        cC2.setPercentWidth(33);

        cC3 = new ColumnConstraints();
        cC3.setPercentWidth(20);


        cC4 = new ColumnConstraints();
        cC4.setPercentWidth(60);

        //RowConstraints rc = new RowConstraints(50); Ggf um Höhe anzupassen


        gridPane1.getColumnConstraints().addAll(cC1);

        gridPane2.getColumnConstraints().addAll(cC2, cC2, cC2);

        gridPane3.getColumnConstraints().addAll(cC3, cC4, cC3);


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
        intervall = new LabelHelper("");

        // Set Button
        closeButton = new Button("Beenden");
        closeButton.setStyle("-fx-background-color: #B0C4DE; "); //Change Button Color

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

        gridPane1.add(gridPane2,0,0);
        gridPane1.add(gridPane3,0,1);


        GridPane liveData = new GridPane();
        cC1.setPercentWidth(100);
        liveData.getColumnConstraints().addAll(cC1);
        liveData.getChildren().add(status);
        //GridPane.setHalignment(status, HPos.CENTER); Zentrierung nicht passen aufgrund Animation

        gridPane2.add(liveData, 1, 0);
        liveData.add(gewicht, 0, 1);
        GridPane.setHalignment(gewicht, HPos.CENTER);
        gridPane2.add(closeButton,2,0);
        GridPane.setHalignment(closeButton, HPos.RIGHT);

        GridPane amountData = new GridPane();
        amountData.getColumnConstraints().addAll(cC1);

        gridPane3.add(amountData, 1,0);
        amountData.add(anzahl, 0, 1);
        GridPane.setHalignment(anzahl, HPos.CENTER);
        amountData.add(intervall,0,2);
        GridPane.setHalignment(intervall, HPos.CENTER);



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

            case 3:
                intervall.setText(text);
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

            case 3:
                intervall.animateLabelText(initialText, endText);
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
