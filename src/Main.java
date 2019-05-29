
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

        Thread thread = new Thread(){
            public void run(){
                NFCController nfcController = new NFCController();
            }
        };

        // Todo: Automatischer Start von StartNFC bei Constructuor Aufruf
        thread.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void startScaleController(Retoure retoure) {
        ScaleController scaleController = new ScaleController(retoure);
    }

    public static void startCalculator() {
        Calculator calculator = new Calculator(new Retoure(12, "Bretzel"));
        calculator.calculateAmount();
    }

}