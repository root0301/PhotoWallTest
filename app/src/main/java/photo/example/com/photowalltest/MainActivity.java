package photo.example.com.photowalltest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slience on 2016/8/3.
 */
public class MainActivity extends Activity {

    private GridView photoWall;

/*    private PhotoWallAdapter adapter;*/

    private RecyclerView recyclerView;

    private PhotoRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        adapter = new PhotoRecyclerAdapter(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
