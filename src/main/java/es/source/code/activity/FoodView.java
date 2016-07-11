package es.source.code.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.source.code.model.User;
import es.source.code.service.ServerObserverService;
import es.source.code.view.FoodFragment;
import es.source.code.view.FoodListFragmentPagerAdapter;

public class FoodView extends AppCompatActivity {
    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
    private List<ListFragment> listFragmentList;
    private List<String> titleList;
    private ActionBar actionBar;
    private User user;

    private String[] coldItem = {"凉拌藕", "凉拌豆腐皮", "刀拍黄瓜", "皮蛋拌豆腐", "凉拌海带丝"};
    private int[] coldIcon = {R.drawable.liangbanou, R.drawable.liangbandoufupi, R.drawable.daopaihuanggua, R.drawable.pidanbandoufu, R.drawable.liangbanhaidai};
    private String[] coldPrice = {"￥10", "￥8", "￥12", "￥15", "￥9"};
    private String[] coldNum = {"13", "9", "20", "15", "16"};

    private String[] hotItem = {"青椒肉丝", "水煮鱼", "红烧排骨", "小炒地三鲜", "蚝油生菜", "红烧小鸡翅"};
    private int[] hotIcon = {R.drawable.qingjiaorousi, R.drawable.shuizhuyu, R.drawable.hongshaopaigu, R.drawable.xiaocaodisanxian, R.drawable.haoyoushengcai, R.drawable.jichi};
    private String[] hotPrice = {"￥30", "￥70", "￥35", "￥25", "￥19", "￥40"};
    private String[] hotNum = {"25", "19", "30", "22", "42", "33"};

    private String[] seafoodItem = {"香辣虾", "香辣蟹", "蒜蓉扇贝", "椒盐濑尿虾", "豉香带鱼"};
    private int[] seafoodIcon = {R.drawable.xianglaxia, R.drawable.xianglaxie, R.drawable.shanbei, R.drawable.nailiao, R.drawable.daiyu};
    private String[] seafoodPrice = {"￥90", "￥110", "￥52", "￥88", "￥49"};
    private String[] seafoodNum = {"12", "19", "28", "15", "14"};

    private String[] drinkItem = {"青岛啤酒", "五粮液", "茅台", "张裕解百纳", "长城干红"};
    private int[] drinkIcon = {R.drawable.qingdao, R.drawable.wuliangye, R.drawable.maotai, R.drawable.zhangyu, R.drawable.changcheng};
    private String[] drinkPrice = {"￥10", "￥688", "￥1288", "￥115", "￥99"};
    private String[] drinkNum = {"108", "19", "10", "65", "34"};

    private Handler sMessageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 10) {
                Bundle bundle = sMessageHandler.obtainMessage().getData();
                String[] foodItem = bundle.getStringArray("foodItem");
                String[] foodNum = bundle.getStringArray("foodNum");

                seafoodItem = foodItem;
                seafoodNum = foodNum;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);

        viewPager = (ViewPager) findViewById(R.id.food_view_pager);
        actionBar = getSupportActionBar();

        Intent userIntent = this.getIntent();
        user = (User) userIntent.getSerializableExtra("User");

        pagerTabStrip = (PagerTabStrip) findViewById(R.id.food_view_pager_tab);
        pagerTabStrip.setTabIndicatorColor(Color.WHITE);
        pagerTabStrip.setTextColor(Color.WHITE);
        pagerTabStrip.setTextSize(0, 60);
        pagerTabStrip.setClickable(false);
        pagerTabStrip.setTextSpacing(80);
        pagerTabStrip.setBackgroundColor(Color.parseColor("#668B8B"));
        pagerTabStrip.setDrawFullUnderline(true);

        FoodFragment cold = new FoodFragment();
        cold.setFoodItem(coldItem);
        cold.setFoodItemIcon(coldIcon);
        cold.setFoodItemPrice(coldPrice);
        cold.setFoodItemNum(coldNum);

        FoodFragment hot = new FoodFragment();
        hot.setFoodItem(hotItem);
        hot.setFoodItemIcon(hotIcon);
        hot.setFoodItemPrice(hotPrice);
        hot.setFoodItemNum(hotNum);

        FoodFragment seafood = new FoodFragment();
        seafood.setFoodItem(seafoodItem);
        seafood.setFoodItemIcon(seafoodIcon);
        seafood.setFoodItemPrice(seafoodPrice);
        seafood.setFoodItemNum(seafoodNum);

        FoodFragment drink = new FoodFragment();
        drink.setFoodItem(drinkItem);
        drink.setFoodItemIcon(drinkIcon);
        drink.setFoodItemPrice(drinkPrice);
        drink.setFoodItemNum(drinkNum);

        listFragmentList = new ArrayList<>();
        listFragmentList.add(cold);
        listFragmentList.add(hot);
        listFragmentList.add(seafood);
        listFragmentList.add(drink);

        titleList = new ArrayList<>();
        titleList.add("冷菜");
        titleList.add("热菜");
        titleList.add("海鲜");
        titleList.add("酒水");

        FoodListFragmentPagerAdapter foodListFragmentPagerAdapter = new FoodListFragmentPagerAdapter(getSupportFragmentManager(), listFragmentList, titleList);
        foodListFragmentPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(foodListFragmentPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_view_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbar_disorder:
                Intent disorderIntent = new Intent(FoodView.this, FoodOrderView.class);
                disorderIntent.putExtra("User", user);
                disorderIntent.putExtra("Page", 1);
                startActivity(disorderIntent);
                break;
            case R.id.actionbar_order:
                Intent orderIntent = new Intent(FoodView.this, FoodOrderView.class);
                orderIntent.putExtra("User", user);
                orderIntent.putExtra("Page", 0);
                startActivity(orderIntent);
                break;
            case R.id.actionbar_call_service:

                break;
            case R.id.actionbar_realtime_update:
                Intent serviceIntent = new Intent(this, ServerObserverService.class);
                Message msg = new Message();
                if (item.getTitle().toString().equals("启动实时更新")) {
                    msg.what = 1;
                    serviceIntent.putExtra("Msg", msg);
                    startService(serviceIntent);
                    item.setTitle("停止实时更新");
                } else if (item.getTitle().toString().equals("停止实时更新")) {
                    msg.what = 0;
                    serviceIntent.putExtra("Msg", msg);
                    startService(serviceIntent);
                    item.setTitle("启动实时更新");
                }
                break;
        }
        return true;
    }
}
