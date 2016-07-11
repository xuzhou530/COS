package es.source.code.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import es.source.code.view.FoodFragmentPagerAdapter;
import es.source.code.view.FoodInfoFragment;

public class FoodDetailed extends AppCompatActivity {
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private FoodInfoFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detailed);

        viewPager = (ViewPager) findViewById(R.id.food_view_pager);

        Intent intent = this.getIntent();
        String item = intent.getStringExtra("Item");
        int icon = intent.getIntExtra("Icon", 0);
        String price = intent.getStringExtra("Price");
        boolean pressed = intent.getBooleanExtra("Pressed", false);

        String[] foodItem = intent.getStringArrayExtra("FoodItem");
        int[] foodIcon = intent.getIntArrayExtra("FoodIcon");
        String[] foodPrice = intent.getStringArrayExtra("FoodPrice");

        fragmentList = new ArrayList<>();
        currentFragment = new FoodInfoFragment();
        currentFragment.setItem(item);
        currentFragment.setIcon(icon);
        currentFragment.setPrice(price);
        currentFragment.setPressed(pressed);
        fragmentList.add(currentFragment);

        for (int i=0; i<foodItem.length; i++) {
            if (!foodItem[i].equals(item)) {
                FoodInfoFragment fragment = new FoodInfoFragment();
                fragment.setItem(foodItem[i]);
                fragment.setIcon(foodIcon[i]);
                fragment.setPrice(foodPrice[i]);
                fragmentList.add(fragment);
            }
        }

        viewPager.setAdapter(new FoodFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
    }
}
