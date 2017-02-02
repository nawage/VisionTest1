package org.usfirst.frc.team3418.robot;

import org.opencv.core.Mat;
//import org.opencv.core.MatOfInt;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Vision {

	static Vision mInstance = new Vision();

    public static Vision getInstance() {
        return mInstance;
    }
    
	boolean allowCam1 = false;
	UsbCamera camera1;
	UsbCamera camera2;
	CvSink cvSink1;
	CvSink cvSink2;
	CvSource outputStream;
	Mat image;
	
	
	public Vision(){
		
		camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera1.setResolution(320, 240);
        camera1.setFPS(30);
        camera2 = CameraServer.getInstance().startAutomaticCapture(1);
        camera2.setResolution(320, 240);
        camera2.setFPS(30);
        image = new Mat();
        
		Thread t = new Thread(() -> {
	        cvSink1 = CameraServer.getInstance().getVideo(camera1);
	        cvSink2 = CameraServer.getInstance().getVideo(camera2);
	        outputStream = CameraServer.getInstance().putVideo("Switcher", 320, 240);
	        
	        
	        while(!Thread.interrupted()) {
	        	if(true) {
	        		allowCam1 = !allowCam1;
	         	}
	         	
	             if(allowCam1){
	               cvSink2.setEnabled(false);
	               cvSink1.setEnabled(true);
	               cvSink1.grabFrame(image);
	             } else{
	               cvSink1.setEnabled(false);
	               cvSink2.setEnabled(true);
	               cvSink2.grabFrame(image);     
	             }
	             
	             outputStream.putFrame(image);
	             }
	         
		});
		t.start();
	}
}
