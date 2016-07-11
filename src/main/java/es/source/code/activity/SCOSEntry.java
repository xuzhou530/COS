package es.source.code.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import es.source.code.activity.R;
import es.source.code.service.ServerObserverService;

public class SCOSEntry extends AppCompatActivity {
    private float startX, startY, offsetX, offsetY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                offsetX = event.getX() - startX;
                offsetY = event.getY() - startY;

                if (Math.abs(offsetX) > Math.abs(offsetY)) {
                    if (offsetX < -10) {
                        Intent intent = new Intent(SCOSEntry.this, MainScreen.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Left", "FromEntry");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
                break;
        }
        return true;
    }
}
