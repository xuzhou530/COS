package es.source.code.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import es.source.code.model.User;
import es.source.code.view.FoodDisorderFragment;
import es.source.code.view.FoodListFragmentPagerAdapter;
import es.source.code.view.FoodOrderFragment;

public class FoodOrderView extends AppCompatActivity {
    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
    private List<ListFragment> fragmentList;
    private List<String> titleList;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order_view);

        viewPager = (ViewPager) findViewById(R.id.food_order_view_pager);
        Intent intent = this.getIntent();
        user = (User) intent.getSerializableExtra("User");
        int page = intent.getIntExtra("Page", 0);
        viewPager.setCurrentItem(page);

        pagerTabStrip = (PagerTabStrip) findViewById(R.id.food_order_view_pager_tab);
        pagerTabStrip.setTabIndicatorColor(Color.WHITE);
        pagerTabStrip.setTextColor(Color.WHITE);
        pagerTabStrip.setTextSize(0, 60);
        pagerTabStrip.setClickable(false);
        pagerTabStrip.setTextSpacing(80);
        pagerTabStrip.setBackgroundColor(Color.parseColor("#668B8B"));
        pagerTabStrip.setDrawFullUnderline(true);

        ListFragment orderedFragment = new FoodOrderFragment();
        ListFragment disorderedFragment = new FoodDisorderFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(orderedFragment);
        fragmentList.add(disorderedFragment);

        titleList = new ArrayList<>();
        titleList.add("已下菜单");
        titleList.add("未下菜单");

        viewPager.setAdapter(new FoodListFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
    }
}
