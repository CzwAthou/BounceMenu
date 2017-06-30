package com.athou.bouncemenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private MyAdapter myAdapter;
    private List<String> stringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stringList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            stringList.add("Data_" + i);
        }

        myAdapter = new MyAdapter(this, stringList) {
            @Override
            protected int ItemLayoutId() {
                return R.layout.item;
            }

            @Override
            protected void onBindHolder(MyViewHolder myViewHolder, int position) {
                TextView textView = myViewHolder.getTextView(R.id.text);
                textView.setText(stringList.get(position));
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Go!").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    BounceMenu bounceMenu;

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (bounceMenu == null) {
            bounceMenu = BounceMenu.makeMenu(this, R.layout.layout_bounce_menu, myAdapter);
            bounceMenu.show();
        } else {
            bounceMenu.dismiss();
            bounceMenu = null;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
