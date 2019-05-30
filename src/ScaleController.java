import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;
import javafx.application.Platform;

import java.util.ArrayList;

public class ScaleController extends SerialController {
    private Retoure retoure;
    private ArrayList<Character> chars;

    public ScaleController(Retoure retoure) {
        this.retoure = retoure;
        setLabelText("Warte auf Gewicht...", 1);
    }

    /**
     *
     */
    public void startNFC() {
        // Todo: Ports auslesen bei Julia --> String möglich?
        SerialPort comPort = SerialPort.getCommPorts()[7];
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

                System.out.println("\n");
            }
        });
    }



}
