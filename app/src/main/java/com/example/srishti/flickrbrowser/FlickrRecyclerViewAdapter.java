package com.example.srishti.flickrbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.srishti.flickrbrowser.Pojo.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Srishti on 20-12-2017.
 */

public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrImageViewHolder> {
    private List<Photo> mPhotosList;
    private Context mContext;
    private final String LOG_TAG = FlickrRecyclerViewAdapter.class.getSimpleName();

    public FlickrRecyclerViewAdapter(Context context, List<Photo> photosList) {
        mContext = context;
        this.mPhotosList = photosList;
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.browse, null);
        FlickrImageViewHolder flickrImageViewHolder = new FlickrImageViewHolder(view);
        return flickrImageViewHolder;
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder flickrImageViewHolder, int position) {
        Photo photoItem = mPhotosList.get(position);
        Log.d(LOG_TAG, "Processing: " + photoItem.getmTitle() + " --> " + Integer.toString(position));

        Picasso.with(mContext).load(photoItem.getmImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(flickrImageViewHolder.thumbnail);
        flickrImageViewHolder.title.setText(photoItem.getmTitle());
    }

    @Override
    public int getItemCount() {
        return (null != mPhotosList ? mPhotosList.size() : 0);
    }

    public void loadNewData(List<Photo> newPhotos) {

        mPhotosList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int pos) {
        return (null != mPhotosList ? mPhotosList.get(pos) : null);
    }
}