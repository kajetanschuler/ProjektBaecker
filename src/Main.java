
import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;

import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {

    public static DisplayManager displayManager;

    @Override
    public void start(Stage primaryStage) throws Exception {
        displayManager = new DisplayManager(primaryStage);


        SerialPort comPort[] = SerialPort.getCommPorts();

        NFCController nfcController = new NFCController();

        Thread thread = new Thread(){
            public void run(){
                nfcController.startNFC();
            }
        };

        thread.start();
        /*String test = "00 21 12 31 34 12  Fil:15....\n " +
                "02 20 12 34 13 14 Art:Dinkelbrot...";
        //test = test.replaceAll("\\s", "");
        String parts[] = test.split("\n+");
        System.out.println(parts[0]);
        System.out.println(parts[1]);
*/
/*        if (test.contains("Fil")) {
            int index = test.indexOf(":");
            System.out.println(index);
            int index2 = test.indexOf(".");
            System.out.println(index2);
        }*/


    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setText(String text, int labelId) {
        displayManager.setLabelText(text, labelId);
    }
}