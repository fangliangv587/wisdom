package com.xz.cenco.test.baidu_police_vertify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * 修改摄像头前后见  initCamera方法
 *
 * @author Administrator
 */
public class CameraView extends SurfaceView implements Callback, PreviewCallback {

    /**
     * log标记
     */
    private static final String tag = CameraView.class.getName();

    /**
     * 相机
     */
    private Camera camera;

    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private SurfaceHolder holder;

    /**
     * 可能有多个组件同时监�?
     */
    private List<CameraListener> listeners;

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        init();
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    public CameraView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    private void init() {
        getHolder().addCallback(this);
    }


    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    /**
     * 初始化相�?
     */
    private void initCamera(SurfaceHolder holder) {

        this.holder = holder;


        //某些系统只有一个摄像头的情况下传入0或1崩溃
        if (Camera.getNumberOfCameras() > 1) {
            //0后  1 前
            camera = Camera.open(cameraId);
        } else {
            camera = Camera.open();
        }

        Log.i("xquestion1", "initCamera");
        startCamera(holder);


    }

    private void startCamera(SurfaceHolder holder) {
        if (camera == null)
            return;

        try {
            camera.setPreviewCallback(this);
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reverseCamera() {
        if (Camera.getNumberOfCameras() <= 1) {
            return;
        }

        if (holder == null) {
            return;
        }

        stopCamera();

        cameraId = cameraId == Camera.CameraInfo.CAMERA_FACING_BACK ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
        camera = Camera.open(cameraId);//打开当前选中的摄像头


        startCamera(holder);


    }


    /**
     * 停止相机
     */
    public void stopCamera() {
        if (camera == null) {
            return;
        }
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        initCamera(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        stopCamera();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        // TODO Auto-generated method stub
        Log.d(tag, data.toString() + ",onPreviewFrame,thread:" + Thread.currentThread().getName());

        if (listeners != null) {
            Size size = camera.getParameters().getPreviewSize();
            for (CameraListener listener : listeners) {
                listener.onCameraFrameData(data, size);
            }

        }

    }


    /**
     * 增加监听
     *
     * @param listener
     */
    public void addListener(CameraListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }

        listeners.add(listener);
    }

    public void removeListener(CameraListener listener) {
        if (listeners == null) {
            return;
        }

        listeners.remove(listener);
    }

    public void removeAllListener() {
        if (listeners == null) {
            return;
        }

        listeners.clear();
    }


    /**
     * 相机监听
     *
     * @author Administrator
     */
    public interface CameraListener {

        /**
         * 每一帧的数据
         *
         * @param data
         */
        void onCameraFrameData(byte[] data, Size previewSize);
    }


}
