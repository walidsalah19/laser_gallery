package economical.economical.economical.multiple_imageview;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import economical.economical.economical.R;

public class imageview {
    private RecyclerView recyclerView;
    private Fragment fragment;
    private ArrayList<String> arrayList;
   public imageview(Fragment fragment,RecyclerView recyclerView,ArrayList<String> arrayList)
    {
        this.fragment = fragment;
        this.recyclerView=recyclerView;
        this.arrayList=arrayList;
      fullrecycler();
    }
    private void fullrecycler()
    {
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(fragment.getActivity());//using to show items in recyclerView if vertical or horizontal
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager( linearLayoutManager);
        recyclerView.setKeepScreenOn(true);
        recyclerView.setHasFixedSize(true);
        adapter adapter = new adapter(arrayList,fragment);
        recyclerView.setAdapter(adapter);
      //  autoscall();
    }
    //using to move items in  RecyclerView  and coped from stackoverflow (autoscall recyclerView item)
    private void autoscall()
    {
        final int speedScroll = 2000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;
            @Override
            public void run() {
                if (count <arrayList.size()) {
                    if (count == arrayList.size() - 1) {
                        flag = false;
                    } else if (count == 0) {
                        flag = true;
                    }
                    if (flag) count++;
                    else count--;

                    recyclerView.smoothScrollToPosition(count);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }

}
