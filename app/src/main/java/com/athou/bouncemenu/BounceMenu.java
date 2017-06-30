package com.athou.bouncemenu;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/6/30.
 */

public class BounceMenu {
    private ViewGroup parentVG;
    private View rootView;
    private BounceView bounceView;
    private RecyclerView recyclerView;

    public BounceMenu(Activity activity, int resId, final MyAdapter adapter) {
        parentVG = (FrameLayout) activity.getWindow().getDecorView().findViewById(android.R.id.content);

        rootView = LayoutInflater.from(activity).inflate(resId, null);
        bounceView = (BounceView) rootView.findViewById(R.id.bounceview);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        bounceView.setAnimatorListener(new BounceView.BounceAnimatorListener() {
            @Override
            public void showContent() {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(adapter);
                recyclerView.scheduleLayoutAnimation();
            }
        });
    }

    public static BounceMenu makeMenu(Activity activity, int resId, MyAdapter adapter) {
        return new BounceMenu(activity, resId, adapter);
    }

    public void show() {
        if (rootView != null) {
            parentVG.removeView(rootView);
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        parentVG.addView(rootView, layoutParams);
        bounceView.show();
    }

    public void dismiss(){
        if (rootView != null) {
            parentVG.removeView(rootView);
        }
    }
}
