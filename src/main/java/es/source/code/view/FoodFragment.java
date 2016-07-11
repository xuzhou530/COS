package es.source.code.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import es.source.code.activity.FoodDetailed;
import es.source.code.activity.R;

public class FoodFragment extends ListFragment {
    private FoodAdapter foodAdapter;
    private String[] foodItem;
    private int[] foodItemIcon;
    private String[] foodItemPrice;
    private String[] foodItemNum;
    private boolean pressed = false;

    public FoodFragment() {
        super();
    }

    public void setFoodItem(String[] foodItem) {
        this.foodItem = foodItem;
    }

    public void setFoodItemIcon(int[] foodItemIcon) {
        this.foodItemIcon = foodItemIcon;
    }

    public void setFoodItemPrice(String[] foodItemPrice) {
        this.foodItemPrice = foodItemPrice;
    }

    public void setFoodItemNum(String[] foodItemNum) {
        this.foodItemNum = foodItemNum;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_view_fragment, container, false);
        foodAdapter = new FoodAdapter(getContext(), R.layout.food_view_list_view);
        setListAdapter(foodAdapter);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //当前 item
        Intent intent = new Intent(getActivity(), FoodDetailed.class);
        intent.putExtra("Item", foodItem[position]);
        intent.putExtra("Icon", foodItemIcon[position]);
        intent.putExtra("Price", foodItemPrice[position]);
        intent.putExtra("Pressed", pressed);

        //传递 List 信息
        intent.putExtra("FoodItem", foodItem);
        intent.putExtra("FoodIcon", foodItemIcon);
        intent.putExtra("FoodPrice", foodItemPrice);

        startActivity(intent);
    }

    class FoodAdapter extends ArrayAdapter<Object> {
        Context context;
        int resource;

        public FoodAdapter(Context context, @LayoutRes int resource) {
            super(context, resource);
            this.resource = resource;
            this.context = context;
        }

        @Override
        public int getCount() {
            return foodItem.length;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView foodImage;
            TextView foodText;
            TextView foodPrice;
            TextView foodNum;
            final ImageButton imageButton;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(resource, null);
            }

            foodImage = (ImageView) convertView.findViewById(R.id.food_image);
            foodImage.setImageResource(foodItemIcon[position]);

            foodText = (TextView) convertView.findViewById(R.id.food_text);
            foodText.setText(foodItem[position]);

            foodPrice = (TextView) convertView.findViewById(R.id.food_price);
            foodPrice.setText(foodItemPrice[position]);

            foodNum = (TextView) convertView.findViewById(R.id.food_num);
            foodNum.setText(foodItemNum[position]);

            imageButton = (ImageButton) convertView.findViewById(R.id.food_button_add);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!pressed) {
                        Toast.makeText(getActivity(), "点菜成功, 已加入菜单", Toast.LENGTH_SHORT).show();
                        imageButton.setImageResource(R.drawable.minus);
                        pressed = true;
                    } else {
                        Toast.makeText(getActivity(), "退点成功, 已从菜单删除", Toast.LENGTH_SHORT).show();
                        imageButton.setImageResource(R.drawable.add);
                        pressed = false;
                    }
                }
            });
            return convertView;
        }
    }
}
