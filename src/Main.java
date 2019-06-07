
import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;

import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {

    final String MAC_NFC_PORT = "tty.usbserial-141";
    final String MAC_SCALE_PORT = "tty.usbserial-A907EH5J";
    final String WIN_NFC_PORT = "CH340";
    final String WIN_SCALE_PORT = "FT232R";

    public static DisplayManager displayManager;
    public static Init artikelData;
    public static String OS = System.getProperty("os.name").toLowerCase();
    public static SerialPort comPort[] = SerialPort.getCommPorts();

    public static int scalePort;
    public int NFCPort;

    @Override
    public void start(Stage primaryStage) throws Exception {
        NFCPort = getNFCPort();
        scalePort = getScalePort();
        displayManager = new DisplayManager(primaryStage);
        artikelData = new Init();

        //Start Calculator without ScaleController for Testing
        //startCalculator();

        // Testing: Which comPorts exist?
        //SerialPort comPort[] = SerialPort.getCommPorts();

        // Start NFC Detection in new Thread

        Thread nfcThread = new Thread() {
            public void run() {
                NFCController nfcController = new NFCController(NFCPort);
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
                ScaleController scaleController = new ScaleController(retoure, scalePort);
            }
        };
        scaleThread.start();
    }


    public static void startCalculator(Retoure retoure) {
        Calculator calculator = new Calculator(retoure);
        calculator.calculateAmount();
    }


     //Function for Testing without ScaleController
//    public static void startCalculator() {
//        Calculator calculator = new Calculator(new Retoure(12,"Brezel",1000));
//        calculator.calculateAmount();
//   }



    private int getNFCPort() {
        String portName;
        if (OS.contains("windows")) {
            for (int i = 0; i < comPort.length; i++) {
                if (comPort[i].getDescriptivePortName().contains(WIN_NFC_PORT)) {
                    return i;
                }
            }
        }

        else if (OS.contains("mac")) {
            for (int i = 0; i < comPort.length; i++) {
                if (comPort[i].getSystemPortName().contains(MAC_NFC_PORT)) {
                    return i;
                }
            }
        }

        else {

            return -1;

        }

        return -1;

    }

    public int getScalePort() {
        String portName;
        if (OS.contains("windows")) {
            for (int i = 0; i < comPort.length; i++) {
                if (comPort[i].getPortDescription().contains(WIN_SCALE_PORT)) {
                    return i;
                }
            }
        }

        else if (OS.contains("mac")) {
            for (int i = 0; i < comPort.length; i++) {
                if (comPort[i].getSystemPortName().equals(MAC_SCALE_PORT)) {
                    return i;
                }



            }
        }

        else {

            return -1;

        }

        return -1;
    }


}