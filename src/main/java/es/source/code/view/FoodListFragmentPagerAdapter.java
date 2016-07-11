package es.source.code.view;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

import java.util.List;

public class FoodListFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<ListFragment> listFragmentList;
    private List<String> titleList;

    public FoodListFragmentPagerAdapter(FragmentManager fragmentManager,
                                        List<ListFragment> listFragmentList, List<String> titleList) {
        super(fragmentManager);
        this.listFragmentList = listFragmentList;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        return listFragmentList.size();
    }

    @Override
    public ListFragment getItem(int position) {
        return listFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
