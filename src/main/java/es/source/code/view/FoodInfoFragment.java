package es.source.code.view;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import es.source.code.activity.R;

public class FoodInfoFragment extends Fragment {
    private String item;
    private int icon;
    private String price;
    private boolean pressed;

    public FoodInfoFragment() {
        super();
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_detailed_fragment, container, false);

        ImageView foodImage = (ImageView) view.findViewById(R.id.food_detail_image);
        foodImage.setImageResource(icon);

        TextView foodItem = (TextView) view.findViewById(R.id.food_detail_item);
        foodItem.setText(item);

        TextView foodPrice = (TextView) view.findViewById(R.id.food_detail_price);
        foodPrice.setText(price);

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.food_detail_button_add);
        if (pressed) {
            imageButton.setImageResource(R.drawable.minus);
        }

        EditText foodEdit = (EditText) view.findViewById(R.id.food_detail_edit);
        return view;
    }
}
