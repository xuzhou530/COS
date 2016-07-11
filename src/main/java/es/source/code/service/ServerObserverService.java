package es.source.code.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import java.util.List;

public class ServerObserverService extends Service {
    private Handler cMessageHandler;
    private Thread cMessageThread;
    private Messenger cMessenger;
    private boolean isRunning = true;
    private Message msg;

    public ServerObserverService() {
        cMessageThread = new MessageThread();
        cMessageHandler = new MessageHandler();
        cMessenger = new Messenger(cMessageHandler);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return cMessenger.getBinder();
    }

    class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //启动多线程
                    cMessageThread.start();

                    //判断 SCOS 进程
                    Context context = getApplicationContext();
                    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                    List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = activityManager.getRunningAppProcesses();
                    for (ActivityManager.RunningAppProcessInfo info : runningAppProcessesList) {
                        if (info.processName.equals("SCOS")) {
                            try {
                                cMessageHandler.sendMessage(msg);
                                msg.what = 10;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 0:
                    isRunning = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    class MessageThread extends Thread {
        @Override
        public void run() {
            while (isRunning) {
                try {
                    //接收数据
                    msg = cMessageHandler.obtainMessage();

                    Thread.sleep(300);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
