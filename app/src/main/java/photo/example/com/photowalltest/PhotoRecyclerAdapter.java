package photo.example.com.photowalltest;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by slience on 2016/8/5.
 */
public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.PhotoRecyclerHolder  > {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    public PhotoRecyclerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public PhotoRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoRecyclerHolder(mLayoutInflater.inflate(R.layout.item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(PhotoRecyclerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PhotoRecyclerHolder extends RecyclerView.ViewHolder{

        CardView cardView;

        public PhotoRecyclerHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.item);
        }
    }
}
