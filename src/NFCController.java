import com.fazecast.jSerialComm.*;
import javafx.application.Platform;

import java.io.InputStream;
import java.util.ArrayList;


public class NFCController {

    public ArrayList<Character> chars;
    private String fil;

    public NFCController() {
    }

    public void startNFC () {
        SerialPort comPort = SerialPort.getCommPorts()[7];
        comPort.setBaudRate(115200);
        comPort.openPort();
        chars = new ArrayList<>();
        comPort.addDataListener(new SerialPortPacketListener() {
        @Override
        public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }

        @Override
        public int getPacketSize() { return 136; }

        @Override
        public void serialEvent(SerialPortEvent event)
        {
            byte[] newData = event.getReceivedData();
            System.out.println("Received data of size: " + newData.length);
            for (int i = 0; i < newData.length; ++i) {
                System.out.print((char)newData[i]);
                chars.add((char) newData[i]);
            }

            transformString(chars);


            System.out.println("\n");
        }
    });
    }



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
    }

    private void transformString (ArrayList<Character> data) {
        StringBuilder stringBuilder = new StringBuilder(data.size());
        for (char c: data) {
            stringBuilder.append(c);

        }
        String kiste = stringBuilder.toString();
        kiste = kiste.substring(2);
        String[] parts = kiste.split("\r\n");


        int fil1 = parts[0].indexOf(":") + 1;
        int fil2 = parts[0].indexOf(".");

        int art1 = parts[1].indexOf(":") + 1;
        int art2 = parts[1].indexOf(".");

        fil = "Filiale: " + parts[0].substring(fil1, fil2) + " - Artikel: " + parts[1].substring(art1, art2);

        Platform.runLater(new Runnable() {
            public void run() {
                Main.displayManager.setLabelText(fil, 0);
            }
        });

        chars.clear();

    }

    private void printNewData (byte[] newData) {
        for (int i = 0; i < newData.length; i++) {
            Character c = ((char) newData[i]);
            chars.add(c);
        }
        transformString(chars);
    }

    public ArrayList<Character> getChars() {
        return chars;
    }
}
