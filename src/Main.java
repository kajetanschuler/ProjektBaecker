
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {

    final String MAC_NFC_PORT = "tty.usbserial-14";
    final String MAC_SCALE_PORT = "tty.usbserial-A907EH5J";
    final String WIN_NFC_PORT = "CH340";
    final String WIN_SCALE_PORT = "FT232R";
    final String LIN_NFC_PORT = "ch341";
    final String LIN_SCALE_PORT = "FT232R";

    ArrayList<Character> chars = new ArrayList<>();

    public static DisplayManager displayManager;
    public static Init artikelData;
    public static String OS = System.getProperty("os.name").toLowerCase();
    public static SerialPort comPort[] = SerialPort.getCommPorts();

    public static Timeline timelineNFC;
    public static Timeline timelineReset;

    public static int scalePort;
    public int NFCPort;

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(OS);
        for (int i = 0; i < comPort.length; i++) {
            String name = comPort[i].getSystemPortName();
            String name2 = comPort[i].getDescriptivePortName();
            String name3 = comPort[i].getPortDescription();
            System.out.println("--- " + i + ": " + name + ", " + name2 + ", " + name3 + " ---");
        }
        NFCPort = getNFCPort();
        scalePort = getScalePort();
        System.out.println("Scale port:" + scalePort);

        displayManager = new DisplayManager(primaryStage);
        artikelData = new Init();

        //Start Calculator without ScaleController for Testing
        //startCalculator();

        // Testing: Which comPorts exist?
        //SerialPort comPort[] = SerialPort.getCommPorts();

        // Start NFC Detection in new Thread

        //System.exit(0);

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
/*        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LabelHelper status = displayManager.getStatus();
                status.animateLabelText("Warte auf NFC-Tag", "Warte auf NFC-Tag . . .");
            }
        };

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer();
                timer.schedule(timerTask, 5000);
            }
        });*/
        timelineNFC = new Timeline(new KeyFrame(Duration.millis(20000),
                ae -> displayManager.labelReset()));
        timelineNFC.play();


        Thread scaleThread = new Thread() {
            public void run() {
                ScaleController scaleController = new ScaleController(retoure, scalePort);
            }
        };
        scaleThread.start();


    }


    public static void startCalculator(Retoure retoure) {
        timelineNFC.pause();
        Calculator calculator = new Calculator(retoure);
        calculator.calculateAmount();

    }



    //Function for Testing without ScaleController
//    public static void startCalculator() {
//        Calculator calculator = new Calculator(new Retoure(12,"Brezel",5017));
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

        else if (OS.contains("linux")) {
            for (int i = 0; i < comPort.length; i++) {
                if (comPort[i].getDescriptivePortName().contains(LIN_NFC_PORT)) {
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

        else if (OS.contains("linux")) {
            System.out.println("Checking for: " + LIN_SCALE_PORT);
            for (int i = 0; i < comPort.length; i++) {
                String description = comPort[i].getPortDescription();
                System.out.println("Port Description:" + description);
                if (comPort[i].getPortDescription().contains(LIN_SCALE_PORT)) {
                    return i;
                }
            }
        }

        else {

            System.out.println("What happened?");
            return -1;

        }

        return -1;
    }

}