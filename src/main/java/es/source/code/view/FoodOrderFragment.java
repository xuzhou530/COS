package es.source.code.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.source.code.activity.R;

public class FoodOrderFragment extends ListFragment {
    private SimpleAdapter simpleAdapter;
    private RadioButton foodOrderSubmit;
    private ProgressBar progressBar;

    private String[] orderedItem = {"凉拌藕", "凉拌豆腐皮", "刀拍黄瓜", "皮蛋拌豆腐", "凉拌海带丝"};
    private int[] orderedIcon = {R.drawable.liangbanou, R.drawable.liangbandoufupi, R.drawable.daopaihuanggua, R.drawable.pidanbandoufu, R.drawable.liangbanhaidai};
    private String[] orderedPrice = {"￥10", "￥8", "￥12", "￥15", "￥9"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_order_fragment, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.food_order_progressbar);

        foodOrderSubmit = (RadioButton) view.findViewById(R.id.food_order_submit);
        foodOrderSubmit.setOnClickListener(submitOnClickListener);

        String[] from = {"text", "image", "price"};
        int[] to = {R.id.food_text, R.id.food_image, R.id.food_price};
        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.food_view_list_view, from, to);
        setListAdapter(simpleAdapter);

        return view;
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();

        for (int i=0; i<orderedItem.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("text", orderedItem[i]);
            map.put("image", orderedIcon[i]);
            map.put("price", orderedPrice[i]);
            list.add(map);
        }
        return list;
    }

    private View.OnClickListener submitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ProgressTask progressTask = new ProgressTask(getContext());
            progressTask.execute(60);
            foodOrderSubmit.setEnabled(false);
        }
    };

    class ProgressTask extends AsyncTask<Integer, Integer, String> {
        int i = 0;
        Context context;
        ProgressDialog progress;

        public ProgressTask(Context context) {
            super();
            this.context = context;
            initProgressDialog(context);
        }

        public void initProgressDialog(Context context) {
            progress = new ProgressDialog(context);
            progress.setMessage("正在买单中");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.show();
        }

        @Override
        protected String doInBackground(Integer... params) {
            while (i<=100) {
                publishProgress(i);
                try {
                    Thread.sleep(60);
                    progress.setProgress(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }
            return "执行完毕";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(context, "账单总共 54 元, 积分 54 分", Toast.LENGTH_SHORT).show();
        }
    }
}
