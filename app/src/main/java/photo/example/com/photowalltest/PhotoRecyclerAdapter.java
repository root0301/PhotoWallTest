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

import java.util.List;

/**
 * Created by slience on 2016/8/5.
 */
public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.PhotoRecyclerHolder  > {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private LruCache<String, Bitmap> mLruCache;

    private List<Integer> data;

    private String mCu=null;

    private String[] mUrl;
    private String TAG = "DEBUG";

    public PhotoRecyclerAdapter(Context context,List<Integer> d) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mUrl = Images.allImageUrls;
        data = d;
        Log.d(TAG,"构造方法");
        Log.d(TAG, String.valueOf(mUrl.length));
    }

    @Override
    public PhotoRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoRecyclerHolder(mLayoutInflater.inflate(R.layout.item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(PhotoRecyclerHolder holder, int position) {
        Glide.with(mContext)
                .load(data.get(position))

                .into(holder.imge);
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
