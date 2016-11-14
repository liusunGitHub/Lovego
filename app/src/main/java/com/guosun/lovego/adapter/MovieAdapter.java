package com.guosun.lovego.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guosun.lovego.R;
import com.guosun.lovego.config.ConstsData;
import com.guosun.lovego.entity.Subject;

import java.util.List;

/**
 * Created by liuguosheng on 2016/11/8.
 */
public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Subject> mList;
    private Context mContext;
    private int viewMode = ConstsData.ViewModes.MODE_LIST;//0 是listview

    public void setViewMode(int viewMode) {
        this.viewMode = viewMode;
        notifyDataSetChanged();
    }


    public MovieAdapter(Context context, List<Subject> subjectList) {
        mList = subjectList;
        mContext = context;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewMode == ConstsData.ViewModes.MODE_GRID) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_movie_grid, parent, false);

            viewHolder = new ViewHolderGrid(view);
        }else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_movie_list, parent, false);
             viewHolder = new ViewHolder(view);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (viewMode == ConstsData.ViewModes.MODE_GRID) {
            Subject subject = mList.get(position);
            ViewHolderGrid mViewHolderGrid = (ViewHolderGrid) holder;
            bindGridDataInfo(mViewHolderGrid, subject);
        }else {
            Subject subject = mList.get(position);
            ViewHolder mViewHolder = (ViewHolder) holder;
            bindDataInfo(mViewHolder, subject);
        }

    }

    private void bindDataInfo(ViewHolder mHolder, Subject subject) {
        if (subject == null) {
            return;
        }
        String text = subject.getTitle();
        mHolder.tv_movie_title.setText(text);
        List<String> genres = subject.getGenres();
        StringBuffer sb = new StringBuffer();
        if (genres != null && genres.size() > 0) {
            for (int i = 0; i < genres.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(genres.get(i));
            }
        }
        mHolder.tv_movie_desc.setText(sb.toString());

        String url = "";
        Subject.Avatars images = subject.getImages();
        if (images != null && !TextUtils.isEmpty(images.getMedium())) {
            url = images.getMedium();
        }
        Glide.with(mContext)
                .load(url)
                .into(mHolder.iv_movie);
    }
    private void bindGridDataInfo(ViewHolderGrid mHolder, Subject subject) {
        if (subject == null) {
            return;
        }
        String text = subject.getTitle();
        mHolder.tv_movie_title.setText(text);
        List<String> genres = subject.getGenres();
        StringBuffer sb = new StringBuffer();
        if (genres != null && genres.size() > 0) {
            for (int i = 0; i < genres.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(genres.get(i));
            }
        }
        mHolder.tv_movie_desc.setText(sb.toString());

        String url = "";
        Subject.Avatars images = subject.getImages();
        if (images != null && !TextUtils.isEmpty(images.getMedium())) {
            url = images.getMedium();
        }
        Glide.with(mContext)
                .load(url)
                .into(mHolder.iv_movie);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public  class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_movie;
        public TextView tv_movie_title;
        public TextView tv_movie_desc;

        public ViewHolder(View view) {
            super(view);
            iv_movie = (ImageView) view.findViewById(R.id.iv_movie);
            tv_movie_title = (TextView) view.findViewById(R.id.tv_movie_title);
            tv_movie_desc = (TextView) view.findViewById(R.id.tv_movie_desc);
        }
    }
    public  class ViewHolderGrid extends RecyclerView.ViewHolder {
        public ImageView iv_movie;
        public TextView tv_movie_title;
        public TextView tv_movie_desc;

        public ViewHolderGrid(View view) {
            super(view);
            iv_movie = (ImageView) view.findViewById(R.id.iv_movie);
            tv_movie_title = (TextView) view.findViewById(R.id.tv_movie_title);
            tv_movie_desc = (TextView) view.findViewById(R.id.tv_movie_desc);
        }
    }


}
