package xyz.nasaknights.deepspace.util.camera;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class CameraUtil {
    private static final UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();

    public static final UsbCamera getCamera() {
        return camera;
    }
}
