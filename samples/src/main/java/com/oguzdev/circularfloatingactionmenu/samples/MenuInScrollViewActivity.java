package com.oguzdev.circularfloatingactionmenu.samples;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;

public class MenuInScrollViewActivity extends ActionBarActivity implements FloatingActionMenu.MenuStateChangeListener, ViewTreeObserver.OnScrollChangedListener, View.OnLayoutChangeListener {

    private ArrayList<FloatingActionMenu> menus;
    private FloatingActionMenu currentMenu;
    private FloatingActionMenu bottomMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_in_scroll_view);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        LinearLayout scrollViewBody = (LinearLayout) findViewById(R.id.scrollViewBody);

        menus = new ArrayList<FloatingActionMenu>();

        // add 20 views into body, each with a menu attached
        for(int i=0; i<20; i++) {
            LinearLayout item = (LinearLayout) inflater.inflate(R.layout.item_scroll_view, null, false);

            scrollViewBody.addView(item);

            View mainActionView = item.findViewById(R.id.itemActionView);

            SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
            ImageView rlIcon1 = new ImageView(this);
            ImageView rlIcon2 = new ImageView(this);
            ImageView rlIcon3 = new ImageView(this);

            rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_chat_light));
            rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_camera_light));
            rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_video_light));

            FloatingActionMenu itemMenu = new FloatingActionMenu.Builder(this)
                    .setStartAngle(-45)
                    .setEndAngle(-135)
                    .setRadius(getResources().getDimensionPixelSize(R.dimen.radius_small))
                    .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                    .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                    .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                    // listen state changes of each menu
                    .setStateChangeListener(this)
                    .attachTo(mainActionView)
                    .build();

            //
            menus.add(itemMenu);
        }

        // listen scroll events on root ScrollView
        scrollView.getViewTreeObserver().addOnScrollChangedListener(this);


        findViewById(R.id.buttom_bar_edit_text).clearFocus();

        // Attach a menu to the button in the bottom bar, just to prove that it works.
        View bottomActionButton = findViewById(R.id.bottom_bar_action_button);
        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);

        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_place_light));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_picture_light));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_camera_light));

        bottomMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .setStartAngle(-40)
                .setEndAngle(-90)
                .setRadius(getResources().getDimensionPixelSize(R.dimen.radius_medium))
                .attachTo(bottomActionButton)
                .build();

        // Listen layout (size) changes on a main layout so that we could reposition the bottom menu
        scrollView.addOnLayoutChangeListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_in_scroll_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuOpened(FloatingActionMenu menu) {
        // Only allow one menu to stay open
        for(FloatingActionMenu iMenu : menus) {
            iMenu.close(true);
        }
        // update our current menu reference
        currentMenu = menu;
    }

    @Override
    public void onMenuClosed(FloatingActionMenu menu) {
        // remove our current menu reference
        currentMenu = null;
    }

    @Override
    public void onScrollChanged() {
        // ScrollView is scrolled,
        // coordinates of main action view has changed.
        // We need to update item coordinates of the current open menu.
        if(currentMenu != null) {
            currentMenu.updateItemPositions();
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        // update the position of the menu when the main layout changes size on events like soft keyboard open/close
        if(right - left != 0 && bottom - top != 0 &&
                (oldLeft != left || oldTop != top || oldRight != right || oldBottom != bottom) && bottomMenu != null) {
            bottomMenu.updateItemPositions();
        }
    }
}
