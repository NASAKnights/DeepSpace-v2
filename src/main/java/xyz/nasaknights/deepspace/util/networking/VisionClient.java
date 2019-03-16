package xyz.nasaknights.deepspace.util.networking;

import edu.wpi.first.wpilibj.Spark;
import xyz.nasaknights.deepspace.RobotMap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class VisionClient {
    private static final Spark light = new Spark(0);
    private static VisionClient instance = new VisionClient();
    private volatile double x = 0.0;
    private volatile double y = 0.0;
    private volatile double rotation = 0.0;
    private final Thread visionClient = new Thread() {
        private Socket clientSocket;
        private DataOutputStream out;
        private BufferedReader in;

        @Override
        public synchronized void start() {
            try {
                clientSocket = new Socket(RobotMap.kVisionServerIP, RobotMap.kVisionServerPort);

                out = new DataOutputStream(clientSocket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    out.writeBytes("poehali\n"); // If you're wondering what 'poehali' means: https://go.bradleyh.me/evhTP

                    String[] result = in.readLine().split(",");

                    x = Double.parseDouble(result[0]);
                    y = Double.parseDouble(result[2]);
                    rotation = Double.parseDouble(result[3]);

                    System.out.println(result[0] + result[2] + result[3]);

                    sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void interrupt() {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static VisionClient getInstance() {
        return instance;
    }

    public void start() {
        if (!visionClient.isAlive()) {
            visionClient.start();
        }
    }

    public void stop() {
        if (visionClient.isAlive()) {
            visionClient.interrupt();
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRotation() {
        return rotation;
    }

    public void setLightOn(boolean on) {
        light.set(on ? 1 : 0);
    }
}
