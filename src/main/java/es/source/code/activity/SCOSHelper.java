package es.source.code.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.commons.mail.HtmlEmail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Handler;

public class SCOSHelper extends AppCompatActivity {
    private GridView gridView;
    private List<Map<String, Object>> list = new ArrayList<>();
    private SimpleAdapter simpleAdapter;
    private String telNumber = "5554";
    private String message = "test scos helper";

    private String[] naviItem = {"用户使用协议", "关于系统", "电话人工帮助", "短信帮助", "邮件帮助"};
    private int[] naviIcon = {R.drawable.deal, R.drawable.system, R.drawable.phone, R.drawable.message, R.drawable.mail};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoshelper);

        gridView = (GridView) findViewById(R.id.scoshelper_grid_view);

        String[] from = {"text", "image"};
        int[] to = {R.id.scoshelper_navi_text, R.id.scoshelper_navi_image};
        simpleAdapter = new SimpleAdapter(this, getData(), R.layout.scoshelper_navi_item, from, to);
        gridView.setAdapter(simpleAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (naviIcon[position]) {
                    case R.drawable.deal:
                        break;
                    case R.drawable.system:
                        break;
                    case R.drawable.phone:
                        Intent telIntent = new Intent();
                        Uri telUri = Uri.parse("tel: "+ telNumber);
                        telIntent.setAction(Intent.ACTION_CALL);
                        telIntent.setData(telUri);
                        startActivity(telIntent);
                        break;
                    case R.drawable.message:
                        SmsManager smsManager = SmsManager.getDefault();

                        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
                        Intent sentIntent = new Intent(SENT_SMS_ACTION);
                        PendingIntent sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0, sentIntent, 0);
                        smsManager.sendTextMessage(telNumber, null, message, null, null);
                        Toast.makeText(getApplicationContext(), "求助短信发送成功!", Toast.LENGTH_SHORT).show();
//                        smsManager.sendTextMessage(telNumber, null, message, sentPI, null);
//                        getApplicationContext().registerReceiver(new BroadcastReceiver() {
//                            @Override
//                            public void onReceive(Context context, Intent intent) {
//                                switch (getResultCode()) {
//                                    case Activity.RESULT_OK:
//                                        Toast.makeText(context, "求助短信发送成功", Toast.LENGTH_SHORT).show();
//                                        break;
//                                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                                        break;
//                                    case SmsManager.RESULT_ERROR_RADIO_OFF:
//                                        break;
//                                    case SmsManager.RESULT_ERROR_NULL_PDU:
//                                        break;
//                                }
//                            }
//                        }, new IntentFilter(SENT_SMS_ACTION));
                        break;
                    case R.drawable.mail:
                        final Handler handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                switch (msg.what) {
                                    case 1:
                                        Toast.makeText(getApplicationContext(), "求助邮件已发送成功!", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                super.handleMessage(msg);
                            }
                        };

                        Runnable send = new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    HtmlEmail email = new HtmlEmail();
                                    email.setHostName("smtp.163.com");
                                    email.setCharset("gbk");
                                    email.addTo("346159150@qq.com");
                                    email.setFrom("lishiyun19@163.com");
                                    email.setAuthentication("lishiyun19", "wsadjkil");
                                    email.setSubject("您好");
                                    email.setMsg("这是我的第一封邮件!");
                                    email.send();

                                    Message msg = new Message();
                                    msg.what = 1;
                                    handler.sendMessage(msg);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        new Thread(send).start();
                        break;
                }
            }
        });
    }

    private List<Map<String, Object>> getData() {
        for (int i=0; i<naviItem.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("text", naviItem[i]);
            map.put("image", naviIcon[i]);
            list.add(map);
        }
        return list;
    }
}
