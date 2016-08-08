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

    public List<Integer> ImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initRecyclerView();
/*        photoWall = (GridView) findViewById(R.id.photo_wall);
        adapter = new PhotoWallAdapter(this,0, Images.allImageUrls, photoWall);
        photoWall.setAdapter(adapter);*/
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        adapter = new PhotoRecyclerAdapter(this,ImageId);
       /* recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    public void add() {
        ImageId = new ArrayList<Integer>();
        ImageId.add(R.drawable.i0);
        ImageId.add(R.drawable.i1);
        ImageId.add(R.drawable.i2);
        ImageId.add(R.drawable.i3);
        ImageId.add(R.drawable.i4);
        ImageId.add(R.drawable.i5);
        ImageId.add(R.drawable.i6);
        ImageId.add(R.drawable.i7);
        ImageId.add(R.drawable.i8);
        ImageId.add(R.drawable.i9);
        ImageId.add(R.drawable.i10);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
/*        adapter.cancelAllTask();*/
    }
}
