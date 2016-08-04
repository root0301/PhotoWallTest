package photo.example.com.photowalltest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by slience on 2016/8/3.
 */
public class PhotoWallAdapter extends ArrayAdapter<String> implements AbsListView.OnScrollListener {


    private String TAG = "DEBUG";

    //记录正在下载或者等待下载的任务
    private Set<BitmapWorkTask> taskCollection;

    //图片缓存核心类
    private LruCache<String, Bitmap> mLruCache;

    //GridView实例
    private GridView mPhotoWall;

    //第一张可见图下标
    private int mFirstVisibleImage;

    //一共可以显示多少张
    private int mVisibleImageCount;

    //是否第一次进入程序
    private boolean isFirstEnter = true;


    public PhotoWallAdapter(Context context, int resource, String[] objects, GridView photoWall) {
        super(context, resource, objects);
        mPhotoWall = photoWall;
        taskCollection = new HashSet<BitmapWorkTask>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/8;
        Log.d(TAG, String.valueOf(cacheSize));
        mLruCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        Log.d(TAG,"构造方法");
        mPhotoWall.setOnScrollListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG,"getView");
        final String url = getItem(position);
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.pthoto_layout,null);
        } else {
            view = convertView;
        }
        final ImageView imageView = (ImageView) view.findViewById(R.id.photo);
        imageView.setTag(url);
        setImageView(url, imageView);
        return view;


    }

    //给imageView添加图片
    private void setImageView(String url, ImageView imageView) {
        Log.d(TAG,"setImageView");
        Bitmap bitmap = getBitmapFromCache(url);
        if(bitmap == null) {
            imageView.setImageResource(R.mipmap.empty);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

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
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            loadBitmaps(mFirstVisibleImage,mVisibleImageCount);
        } else {
            cancelAllTask();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mVisibleImageCount = visibleItemCount;
        mFirstVisibleImage = firstVisibleItem;
        if (isFirstEnter && visibleItemCount > 0 ) {
            loadBitmaps(firstVisibleItem,visibleItemCount);
            Log.d(TAG, "----第一次进来");
            isFirstEnter = false;
        }
    }

    //加载屏幕上的bitmap对象
    private void loadBitmaps(int first, int all) {
        try {
            Log.d(TAG,"loadBitmaps");
            for (int i=first; i<first+all; i++) {
                String imgUrl = Images.allImageUrls[i];
                Bitmap bitmap = getBitmapFromCache(imgUrl);
                if (bitmap == null) {
                    BitmapWorkTask task = new BitmapWorkTask();
                    taskCollection.add(task);
                    task.execute(imgUrl);
                } else {
                    ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imgUrl);
                    if (bitmap != null && imageView != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    //取消所有的下载任务
    public void cancelAllTask() {
        if(taskCollection != null) {
            for (BitmapWorkTask task : taskCollection) {
                task.cancel(false);
            }
        }
    }


    /**
     * 异步下载图片
     */
    class BitmapWorkTask extends AsyncTask<String, Void, Bitmap> {

        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            Bitmap bitmap = downloadBitmap(imageUrl);
            if (bitmap != null) {
                addBitmapToCache(params[0],bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView)mPhotoWall.findViewWithTag(imageUrl);
            if (imageView!=null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            taskCollection.remove(this);
        }

        //从网络下载image
        private Bitmap downloadBitmap(String imageUrl) {
            Bitmap bitmap = null;
            HttpURLConnection conn= null;
            try {
                URL url = new URL(imageUrl);
                conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5*1000);
                conn.setReadTimeout(10 * 10000);
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                Log.d(TAG, "---xiazai");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "---xiazai出错");
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return  bitmap;
        }

    }

}
