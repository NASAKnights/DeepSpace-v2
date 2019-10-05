package xyz.nasaknights.deepspace.util.camera;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class CameraUtil {
    private static final UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);

    public static final UsbCamera getCamera() {
        return camera;
    }

    public static final void prepareCamera() {
        camera.setResolution(400, 300);
        camera.setFPS(50);
//        camera.setPixelFormat(VideoMode.PixelFormat.kMJPEG);
        camera.setExposureManual(50);
    }
}
