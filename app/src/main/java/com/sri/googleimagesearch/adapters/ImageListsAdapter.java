package com.sri.googleimagesearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.sri.googleimagesearch.DetailPageActivity;
import com.sri.googleimagesearch.R;
import com.sri.googleimagesearch.model.Item;
import com.sri.googleimagesearch.model.PageMap;

import java.util.List;

public class ImageListsAdapter extends RecyclerView.Adapter<ImageListsAdapter.ImageListViewHolder> {

    private static final String TAG = ImageListsAdapter.class.getSimpleName();
    private static List<Item> data;
    private static Context context;

    public static class ImageListViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public ImageListViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.image_title);
            imageView = v.findViewById(R.id.image);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String imageSrc = "";
                    String description = "";
                    try {
                        imageSrc = data.get(getAdapterPosition()).getPagemap().getMetatags().get(0).getImageSrc();
                    } catch (NullPointerException e) {
                        Log.d(TAG, "Unable to find full image url. Defaulting to placeholder.");
                    }

                    try {
                        description = data.get(getAdapterPosition()).getPagemap().getMetatags().get(0).getDescription();
                    } catch (NullPointerException e) {
                        Log.d(TAG, "Unable to find description.");
                    }

                    Intent intent = new Intent(context, DetailPageActivity.class);
                    intent.putExtra("title", data.get(getAdapterPosition()).getTitle());
                    intent.putExtra("image", imageSrc);
                    intent.putExtra("description", description);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    public ImageListsAdapter(final List<Item> data, final Context context) {
        super();
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ImageListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageListViewHolder imageListViewHolder, int i) {
        final Item item = data.get(i);

        imageListViewHolder.textView.setText(item.getTitle());

        final String imageUrl = getImageUrl(item);

        if(imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.loading)
                    .into(imageListViewHolder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d(TAG, "Image not found. Using placeholder");
                            imageListViewHolder.imageView.setImageResource(R.drawable.placeholder);
                        }
                    });
        }
        else {
            imageListViewHolder.imageView.setImageResource(R.drawable.placeholder);
        }

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(final Item item) {
        data.add(item);
    }

    public void add(final List<Item> items) {
        data.clear();
        data.addAll(items);
    }

    private String getImageUrl(final Item item) {
        try {
            final List<PageMap.CSEThumbnail> thumbnail = item.getPagemap().getCse_thumbnail();
            final List<PageMap.Website> fullImage = item.getPagemap().getWebsite();
            final List<PageMap.Metatags> metatags = item.getPagemap().getMetatags();
            if (thumbnail != null) {
                return thumbnail.get(0).getSrc();
            } else if (fullImage != null) {
                return fullImage.get(0).getImage();
            }
            return metatags.get(0).getImageSrc();
        } catch (NullPointerException e) {
            return "";
        }
    }

}
