
import javafx.application.Application;

import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {

    public static DisplayManager displayManager;

    @Override
    public void start(Stage primaryStage) throws Exception {
        displayManager = new DisplayManager(primaryStage);
        displayManager.setLabelText("Test", 0);

        NFCController nfcController = new NFCController();
        nfcController.startNFCHandler();



    }

    public static void main(String[] args) {
        launch(args);
    }
}