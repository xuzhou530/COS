package es.source.code.view;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.source.code.activity.R;

public class FoodDisorderFragment extends ListFragment {
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private RadioGroup radioGroup;

    private String[] disorderedItem = {"凉拌藕", "凉拌豆腐皮", "刀拍黄瓜", "皮蛋拌豆腐", "凉拌海带丝"};
    private int[] disorderedIcon = {R.drawable.liangbanou, R.drawable.liangbandoufupi, R.drawable.daopaihuanggua, R.drawable.pidanbandoufu, R.drawable.liangbanhaidai};
    private String[] disorderedPrice = {"￥10", "￥8", "￥12", "￥15", "￥9"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_disorder_fragment, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);
        radioGroup = (RadioGroup) view.findViewById(R.id.food_disorder_tab_menu);

        String[] from = {"text", "image", "price"};
        int[] to = {R.id.food_text, R.id.food_image, R.id.food_price};
        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.food_view_list_view, from, to);
        setListAdapter(simpleAdapter);

        return view;
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();

        for (int i=0; i<disorderedItem.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("text", disorderedItem[i]);
            map.put("image", disorderedIcon[i]);
            map.put("price", disorderedPrice[i]);
            list.add(map);
        }
        return list;
    }
}
