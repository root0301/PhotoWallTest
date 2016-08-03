package photo.example.com.photowalltest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

/**
 * Created by slience on 2016/8/3.
 */
public class MainActivity extends Activity {

    private GridView photoWall;

    private PhotoWallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        photoWall = (GridView) findViewById(R.id.photo_wall);
        adapter = new PhotoWallAdapter(this,0, Images.allImageUrls, photoWall);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cancelAllTask();
    }
}
