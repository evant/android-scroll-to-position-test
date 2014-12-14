package me.tatarka.testsuperlongrecylerview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;

import me.tatarka.testsuperlongrecylerview.holdr.Holdr_ActivityMain;
import me.tatarka.testsuperlongrecylerview.holdr.Holdr_ItemFile;


public class MainActivity extends ActionBarActivity implements Holdr_ActivityMain.Listener {
    Holdr_ActivityMain holdr;
    int onBindViewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Holdr_ActivityMain.LAYOUT);
        holdr = new Holdr_ActivityMain(findViewById(android.R.id.content));
        holdr.setListener(this);
        
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        holdr.recyclerView.setLayoutManager(layoutManager);
        holdr.recyclerView.setAdapter(new Adapter());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollToTopClick(Button scrollToTop) {
        monitorOnBindViewCount();
        holdr.recyclerView.scrollToPosition(0);
    }

    @Override
    public void onScrollToMiddleClick(Button scrollToMiddle) {
        monitorOnBindViewCount();
        holdr.recyclerView.scrollToPosition(holdr.recyclerView.getAdapter().getItemCount() / 2);

    }
    
    @Override
    public void onScrollToBottomClick(Button scrollToBottom) {
        monitorOnBindViewCount();
        holdr.recyclerView.scrollToPosition(holdr.recyclerView.getAdapter().getItemCount() - 1);
    }
    
    private void monitorOnBindViewCount() {
        onBindViewCount = 0;
        // Wait for list to finish scrolling before printing out result.
        holdr.recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("MainActivity", "onBindViewCount: " + onBindViewCount);
            }
        }, 1000);
    } 

    private class Adapter extends RecyclerView.Adapter<Holdr_ItemFile> {
        private LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

        @Override
        public Holdr_ItemFile onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holdr_ItemFile(inflater.inflate(Holdr_ItemFile.LAYOUT, parent, false));
        }

        @Override
        public void onBindViewHolder(Holdr_ItemFile holder, int position) {
            holder.name.setText("Item (" + position + ")");
            onBindViewCount += 1;
        }

        @Override
        public int getItemCount() {
            return 5000;
        }
    }
}
