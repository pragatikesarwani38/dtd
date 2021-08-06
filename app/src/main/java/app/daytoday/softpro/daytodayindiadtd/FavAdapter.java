package app.daytoday.softpro.daytodayindiadtd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hp on 12-08-2019.
 */

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.Exampleviewholder> {
    public List<newsdata> mExamplelist;
    public Context mContext;

    public static class Exampleviewholder extends RecyclerView.ViewHolder {
        public ImageView newsimage;
        public TextView tv_headlines;
        public TextView tv_date;
        public TextView tv_time;
        public LinearLayout itemclick;


        public Exampleviewholder(View itemView) {
            super(itemView);
            newsimage = (ImageView) itemView.findViewById(R.id.news_list_img);
            tv_headlines = itemView.findViewById(R.id.news_head_tv);
            tv_date = itemView.findViewById(R.id.news_date_tv);
            tv_time = itemView.findViewById(R.id.news_time_tv);
            itemclick = itemView.findViewById(R.id.nextactivity);
        }
    }

    public FavAdapter(Context context, List<newsdata> examplelist) {
        mContext = context;
        mExamplelist = examplelist;
    }

    @Override
    public FavAdapter.Exampleviewholder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_news_list, viewGroup, false);
        FavAdapter.Exampleviewholder exampleviewholder = new FavAdapter.Exampleviewholder(view);
        return exampleviewholder;
    }

    @Override
    public void onBindViewHolder(FavAdapter.Exampleviewholder exampleviewholder, int i) {
        final newsdata currentitem = mExamplelist.get(i);
        exampleviewholder.tv_headlines.setText(currentitem.getHeadline());
        exampleviewholder.tv_time.setText(currentitem.getTime());
        exampleviewholder.tv_date.setText(currentitem.getDate());
        Picasso.get().load(currentitem.getImgurl()).into(exampleviewholder.newsimage);
        exampleviewholder.itemclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DestroyActivity.class);
                i.putExtra("head", currentitem.getHeadline());
                i.putExtra("desc", currentitem.getDescription());

                i.putExtra("img", currentitem.getImgurl());
                i.putExtra("key", currentitem.getKey());
                i.putExtra("category", currentitem.getCategory());
                mContext.startActivity(i);
                ((Activity) mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExamplelist.size();
    }
}



