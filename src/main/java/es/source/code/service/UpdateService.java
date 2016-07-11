package es.source.code.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.RemoteViews;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import es.source.code.activity.MainScreen;
import es.source.code.activity.R;
import es.source.code.conf.SCOSConf;

public class UpdateService extends IntentService {
    private String contentTitle;
    private String contentText;

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String params = "update=1";
                String path = SCOSConf.SERVICE + "?" + params;

                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    try {
                        if (in.ready()) {
                            String jsonString = in.readLine();
                            JSONObject jsonObj = new JSONObject(jsonString);

                            String foodNum = jsonObj.getString("foodNum");
                            String foodItem = jsonObj.getString("foodItem");
                            String foodPrice = jsonObj.getString("foodPrice");
                            String foodCate = jsonObj.getString("foodCate");

                            contentTitle = "新品上架: ";
                            contentText = "菜名: " + foodItem + "," + "价格: " + foodPrice + ","
                                    + "数量: " + foodNum + "," + "类型: " + foodCate;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Notification.Builder builder = new Notification.Builder(UpdateService.this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_view);
        remoteViews.setImageViewResource(R.id.iv_notification_image, R.drawable.logo);
        remoteViews.setImageViewResource(R.id.btn_notification_clear, R.drawable.minus);
        remoteViews.setTextViewText(R.id.tv_notification_text, contentTitle+contentText);
        builder.setSmallIcon(R.drawable.logo);
        builder.setAutoCancel(false);
        builder.setContent(remoteViews);

        //点击事件
        Intent notifyIntent = new Intent(UpdateService.this, MainScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.btn_notification_clear, pendingIntent);

        notificationManager.notify(0, builder.build());
    }
}