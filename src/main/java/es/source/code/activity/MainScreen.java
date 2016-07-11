package es.source.code.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.source.code.conf.SCOSConf;
import es.source.code.model.User;

public class MainScreen extends AppCompatActivity {
    private User user;
    private GridView gridView;
    private List<Map<String, Object>> list = new ArrayList<>();
    private SimpleAdapter simpleAdapter;

    //数据
    private String[] naviItem = {"点菜", "订单", "登陆/注册", "帮助"};
    private int[] naviIcon = {R.drawable.food, R.drawable.menu, R.drawable.login, R.drawable.help};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        gridView = (GridView) findViewById(R.id.mainscreen_grid_view);

        String[] from = {"text", "image"};
        int[] to = {R.id.mainscreen_navi_text, R.id.mainscreen_navi_image};
        simpleAdapter = new SimpleAdapter(this, getData(), R.layout.main_screen_navi_item, from, to);
        gridView.setAdapter(simpleAdapter);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (!bundle.isEmpty()) {
            String login = bundle.getString("Login");
            String register = bundle.getString("Register");
            if (login != null && SCOSConf.LOGIN_SUCCESS.equals(login)) {
                Toast.makeText(this, "欢迎您登陆 SCOS", Toast.LENGTH_LONG).show();
                user = (User) intent.getSerializableExtra("LoginUser");
            } else if (register != null && SCOSConf.REGISTER_SUCCESS.equals(register)) {
                Toast.makeText(this, "欢迎您成为 SCOS 新用户", Toast.LENGTH_LONG).show();
            }
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (naviIcon[position]) {
                    case R.drawable.food:
                        Intent foodIntent = new Intent(MainScreen.this, FoodView.class);
                        foodIntent.putExtra("User", user);
                        startActivity(foodIntent);
                        break;
                    case R.drawable.menu:
                        Intent foodOrderIntent = new Intent(MainScreen.this, FoodOrderView.class);
                        foodOrderIntent.putExtra("User", user);
                        startActivity(foodOrderIntent);
                        break;
                    case R.drawable.login:
                        startActivity(new Intent(MainScreen.this, LoginOrRegister.class));
                        break;
                    case R.drawable.help:
                        startActivity(new Intent(MainScreen.this, SCOSHelper.class));
                        break;
                }
            }
        });
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i< naviItem.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("text", naviItem[i]);
            map.put("image", naviIcon[i]);
            list.add(map);
        }
        return list;
    }
}
