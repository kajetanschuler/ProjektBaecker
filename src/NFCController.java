import com.fazecast.jSerialComm.*;
import javafx.application.Platform;

import java.io.InputStream;
import java.util.ArrayList;


public class NFCController extends SerialController {

    public ArrayList<Character> chars;
    private String fil;

    public NFCController() {
        startNFC();

    }

    /**
     *
     */
    public void startNFC() {
        // Todo: Ports auslesen bei Julia --> String möglich?
        SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.setBaudRate(115200);
        comPort.openPort();
        chars = new ArrayList<>();
        comPort.addDataListener(new SerialPortPacketListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public int getPacketSize() {
                return 134;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                byte[] newData = event.getReceivedData();
                System.out.println("Received data of size: " + newData.length);
                for (int i = 0; i < newData.length; ++i) {
                    System.out.print((char) newData[i]);
                    chars.add((char) newData[i]);
                }

                transformString(chars);


                System.out.println("\n");
            }
        });
    }

/*

    public void startNFCHandler () {

    SerialPort comPort = SerialPort.getCommPorts()[7];
    comPort.setBaudRate(115200);
    comPort.openPort();
    chars = new ArrayList<>();
    comPort.addDataListener(new SerialPortDataListener() {
        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
        }

        @Override
        public void serialEvent(SerialPortEvent event)
        {

            byte[] newData = event.getReceivedData();
            System.out.println("Received data of size: " + newData.length);
            for (int i = 0; i < newData.length; i++) {
                chars.add((char) newData[i]);
                transformString(chars);
                //chars.add(c);
                //transformString(chars);
                //System.out.print((char)newData[i]);
            }


            System.out.println("\n");
        }
    });
}

   public void Test () {
        SerialPort comPort = SerialPort.getCommPorts()[7];
        comPort.setBaudRate(115200);
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        InputStream in = comPort.getInputStream();
        try {
            chars = new ArrayList<Character>();

           //for (int j = 0; j < 1000; ++j) {
           //     System.out.print((char)in.read());
           // }

           for (int j = 0; j < 1000; ++j) {
                chars.add((char)in.read());


           }

           transformString(chars);


            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        comPort.closePort();
    }*/

    /**
     *
     * @param data
     */
    private void transformString(ArrayList<Character> data) {

        // Build String from Array List
        StringBuilder stringBuilder = new StringBuilder(data.size());
        for (char c : data) {
            stringBuilder.append(c);

        }
        String kiste = stringBuilder.toString();

        // Split Strings at new line
        String[] parts = kiste.split("\r\n");

        // Extract Indices of relevant data
        int fil1 = parts[0].indexOf(":") + 1;
        int fil2 = parts[0].indexOf(".");
        int art1 = parts[1].indexOf(":") + 1;
        int art2 = parts[1].indexOf(".");

        // Create String/Int for Label and Retoure Object
        try {
            int filiale = Integer.parseInt(parts[0].substring(fil1, fil2));
            String artikel = parts[1].substring(art1, art2);

            fil = "Filiale: " + filiale + " - Artikel: " + artikel;

            // Create Retoure Object from extracted data
            Retoure retoure = new Retoure(filiale, artikel);

            // Give Object to Main and start Scale Controller
            Main.startScaleController(retoure);

            // Update FX Elements through superclass
            setLabelText(fil, 0);


        } catch (Exception e) {
            // Show error when data cannot be extracted from NFC
            Platform.runLater(new Runnable() {
                public void run() {
                    Main.displayManager.setLabelText("Could not retrieve data - please try again!", 0);
                }
            });
        }

        // delete contents of ArrayList for next Serial Event
        chars.clear();

    }
}

// Todo: Timer um Seriellen Port zu resetten
