package photo.example.com.photowalltest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by slience on 2016/8/9.
 */
public class SingleActivity extends Activity {

    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_layout);
        photoView = (PhotoView) findViewById(R.id.photo_view);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        DrawableRequestBuilder<Integer> thumbnailRequest = Glide
                .with(this)
                .load(R.mipmap.aaa);
        Glide.with(this)
                .load(url)
                .thumbnail(thumbnailRequest)
                .into(photoView);
    }



}
