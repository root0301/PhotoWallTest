package photo.example.com.photowalltest;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by slience on 2016/8/5.
 */
public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.PhotoRecyclerHolder  > {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private LruCache<String, Bitmap> mLruCache;

    private String mCu=null;

    private String[] mUrl;
    private String TAG = "DEBUG";

    public PhotoRecyclerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mUrl = Images.allImageUrls;
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/8;
        mLruCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        Log.d(TAG,"构造方法");
        Log.d(TAG, String.valueOf(mUrl.length));
    }

    @Override
    public PhotoRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoRecyclerHolder(mLayoutInflater.inflate(R.layout.item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(PhotoRecyclerHolder holder, int position) {
        mCu = mUrl[position];
        Bitmap bitmap = getBitmapFromCache(mUrl[position]);
        if (bitmap != null) {
            holder.imge.setImageBitmap(bitmap);
        } else {
            Glide.with(mContext)
                    .load(mUrl[position])
                    .fitCenter()
                    .into(holder.imge);
            Glide.with(mContext).load(mUrl[position]).asBitmap().into(target);
        }

    }


    public SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            // do something with the bitmap
            // for demonstration purposes, let's just set it to an ImageView
            addBitmapToCache(mCu,bitmap);
        }
    };


    //添加图片到LruCache
    public void addBitmapToCache(String key, Bitmap bitmap) {
        if(getBitmapFromCache(key) == null) {
            mLruCache.put(key, bitmap);
        }
    }


    //从LruCache中取图片
    public Bitmap getBitmapFromCache(String url) {
        return mLruCache.get(url);
    }


    @Override
    public int getItemCount() {
        return mUrl.length;
    }

    public class PhotoRecyclerHolder extends RecyclerView.ViewHolder{

        ImageView imge;

        public PhotoRecyclerHolder(View itemView) {
            super(itemView);
            imge= (ImageView) itemView.findViewById(R.id.item_photo);
        }
    }
}
