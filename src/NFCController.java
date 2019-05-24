import com.fazecast.jSerialComm.*;

import java.io.InputStream;
import java.util.ArrayList;


public class NFCController {

public void startNFCHandler () {

    SerialPort comPort = SerialPort.getCommPorts()[0];
    comPort.setBaudRate(115200);
    comPort.openPort();
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
            for (int i = 0; i < newData.length; ++i)
                System.out.print((char)newData[i]);
            System.out.println("\n");
        }
    });
}

    public static void main(String[] args) {
        SerialPort comPort = SerialPort.getCommPorts()[7];
        comPort.setBaudRate(115200);
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        InputStream in = comPort.getInputStream();
        try {
            ArrayList<Character> chars = new ArrayList<Character>();

            for (int j = 0; j < 1000; ++j) {
                System.out.print((char)in.read());

            }

/*            for (int j = 0; j < 1000; ++j) {
                chars.add((char)in.read());

            }*/

            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        comPort.closePort();
    }

}
