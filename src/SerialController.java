import com.fazecast.jSerialComm.*;

import java.io.InputStream;
import java.util.ArrayList;


public class SerialController {



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
