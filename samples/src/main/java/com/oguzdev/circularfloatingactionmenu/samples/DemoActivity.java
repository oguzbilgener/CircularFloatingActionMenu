package com.oguzdev.circularfloatingactionmenu.samples;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DemoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ContentFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ContentFragment extends Fragment implements AdapterView.OnItemClickListener {

        private ListView demosListView;

        public ContentFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_demo, container, false);

            String[] items = { "Menu with FloatingActionButton",
                               "Menu attached to custom button",
                               "Menu with custom animation",
                               "Menu in ScrollView",
                               "Menu as system overlay"
                            };
            ArrayAdapter<String> simpleAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
            demosListView = (ListView) rootView.findViewById(R.id.demosListView);
            demosListView.setAdapter(simpleAdapter);
            demosListView.setOnItemClickListener(this);
            return rootView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    startActivity(new Intent(getActivity(), MenuWithFABActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(getActivity(), MenuWithCustomActionButtonActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(getActivity(), MenuWithCustomAnimationActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(getActivity(), MenuInScrollViewActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(getActivity(), SystemOverlayMenuActivity.class));
                    break;
            }
        }
    }
}
