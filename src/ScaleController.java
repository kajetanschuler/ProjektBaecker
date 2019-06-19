import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;
import javafx.application.Platform;

import java.io.InputStream;
import java.util.ArrayList;

public class ScaleController extends SerialController {
    private Retoure retoure;
    private ArrayList<Character> chars;
    private int sPort;

    public ScaleController(Retoure retoure, int sPort) {
        this.retoure = retoure;
        this.sPort = sPort;
        animateLabel("Warte auf Gewicht", "Warte auf Gewicht . . .", 1);
        startScale();
    }

    /**
     *
     */
    public void startScale() {
        SerialPort comPort = SerialPort.getCommPorts()[sPort];
        comPort.setBaudRate(9600);
        comPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
        comPort.openPort();
        chars = new ArrayList<>();
        comPort.addDataListener(new SerialPortPacketListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public int getPacketSize() {
                return 20;
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

    private void transformString(ArrayList<Character> data) {
        // Build String from Array List
        StringBuilder stringBuilder = new StringBuilder(data.size());
        for (char c : data) {
            stringBuilder.append(c);

        }
        String gewicht = stringBuilder.toString();

        gewicht = gewicht.replaceAll("[^\\d.]", "");
        double wGewicht = Double.parseDouble(gewicht);

        setLabelText("Gewicht: " + gewicht + "g", 1);
        retoure.setGewicht(wGewicht);
        Main.startCalculator(retoure);

        if (wGewicht == 0.0) {
            Main.displayManager.labelReset();
        }

        chars.clear();
    }



}
