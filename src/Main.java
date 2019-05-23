import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;

import static javafx.application.Application.launch;

public class Main extends Application {

    private final GridPane gridPane = new GridPane();

    @Override
    public void start(Stage primaryStage) throws Exception {

        // initialize GridPane
        gridPane.setGridLinesVisible(false);

        // Coloumn Constraints for correct width
        ColumnConstraints cC = new ColumnConstraints();
        cC.setPercentWidth(33);
        cC.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().addAll(cC, cC, cC);

        // Prepare and Show Stage
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Projekt Bäcker");
        primaryStage.setFullScreen(true);
        primaryStage.show();

        GridPane liveData = new GridPane();
        cC.setPercentWidth(100);
        liveData.getColumnConstraints().addAll(cC);

        gridPane.add(liveData, 1, 0);

        liveData.add(new Label("Test1"), 0, 0);
        liveData.add(new Label("Test2"), 0, 1);
        liveData.add(new Label("Test3"), 0, 2);



    }

    public static void main(String[] args) {
        launch(args);
    }
}