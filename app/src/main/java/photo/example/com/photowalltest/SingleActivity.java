package photo.example.com.photowalltest;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;

/**
 * Created by slience on 2016/8/9.
 */
public class SingleActivity extends Activity {

    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_layout);
        imageView = (ImageView) findViewById(R.id.single_image);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        DrawableRequestBuilder<Integer> thumbnailRequest = Glide
                .with(this)
                .load(R.mipmap.waitg);
        Glide.with(this)
                .load(url)
                .thumbnail(thumbnailRequest)
                .into(imageView);
    }
}
