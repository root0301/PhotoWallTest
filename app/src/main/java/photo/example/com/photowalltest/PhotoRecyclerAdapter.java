package photo.example.com.photowalltest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
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
        Log.d(TAG,"构造方法");
        Log.d(TAG, String.valueOf(mUrl.length));
    }

    @Override
    public PhotoRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoRecyclerHolder(mLayoutInflater.inflate(R.layout.item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(PhotoRecyclerHolder holder, final int position) {
        DrawableRequestBuilder<Integer> thumbnailRequest = Glide
                .with(mContext)
                .load(R.mipmap.waitg);
        Glide.with(mContext)
                .load(mUrl[position])
                .thumbnail(thumbnailRequest)
                .into(holder.imge);
        holder.imge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SingleActivity.class);
                intent.putExtra("url",mUrl[position]);
                mContext.startActivity(intent);
            }
        });
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
