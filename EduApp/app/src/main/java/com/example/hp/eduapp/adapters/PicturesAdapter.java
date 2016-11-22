package com.example.hp.eduapp.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hp.eduapp.R;
import com.example.hp.eduapp.data_models.Picture;

import java.util.ArrayList;

/**
 * Created by radman on 10/1/2016.
 */
public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.ViewHolder> {

    private Context c;
    private ArrayList<Picture> pics;

    public PicturesAdapter(Context context, ArrayList<Picture> pics) {
        this.c = context;
        this.pics = pics;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View picturesView = inflater.inflate(R.layout.item_pictures, parent, false);
        return new PicturesAdapter.ViewHolder(picturesView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int THUMBSIZE = 96;
        holder.pic.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(pics.get(position)
                .getPicFilePath()), THUMBSIZE, THUMBSIZE)); //make a bitmap image and display it
    }

    @Override
    public int getItemCount() {
        return pics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView pic;

        public ViewHolder(View itemView) {
            super(itemView);
            pic = (ImageView) itemView.findViewById(R.id.item_pictures_imageView);
        }
    }
}
