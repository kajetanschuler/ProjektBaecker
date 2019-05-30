
import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;

import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {

    public static DisplayManager displayManager;
    public static Init artikelData;

    @Override
    public void start(Stage primaryStage) throws Exception {
        displayManager = new DisplayManager(primaryStage);
        artikelData = new Init();
        startCalculator();

        // Testing: Which comPorts exist?
        SerialPort comPort[] = SerialPort.getCommPorts();

        // Start NFC Detection in new Thread

        Thread nfcThread = new Thread() {
            public void run() {
                NFCController nfcController = new NFCController();
            }
        };
        nfcThread.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

    // Start scale Controller from other classes to get weight from scale
    public static void startScaleController(Retoure retoure) {
        Thread scaleThread = new Thread() {
            public void run() {
                ScaleController scaleController = new ScaleController(retoure);

            }
        };
        scaleThread.start();
    }

    public static void startCalculator() {
        Calculator calculator = new Calculator(new Retoure(12, "Bretzel"));
        calculator.calculateAmount();
    }

}