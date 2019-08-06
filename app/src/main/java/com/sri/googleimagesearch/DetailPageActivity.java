package com.sri.googleimagesearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailPageActivity extends AppCompatActivity {

    private static final String TAG = DetailPageActivity.class.getSimpleName();

    private String title;
    private String imageSource;
    private String description;

    private ImageView imageView;
    private TextView titleView;
    private TextView descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        final Intent intent = getIntent();
        title = intent.getStringExtra("title");
        imageSource = intent.getStringExtra("image");
        description = intent.getStringExtra("description");

        imageView = findViewById(R.id.detail_image);
        titleView = findViewById(R.id.detail_title);
        descriptionView = findViewById(R.id.detail_description);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setTitle(title);

        if(imageSource != null && !imageSource.isEmpty()) {
            Picasso.get().load(imageSource).placeholder(R.drawable.loading).into(imageView, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Log.d(TAG, "Image not found. Showing placeholder");
                    imageView.setImageResource(R.drawable.placeholder);
                }
            });
        }
        else {
            imageView.setImageResource(R.drawable.placeholder);
        }

        titleView.setText(title);
        descriptionView.setText(description);
    }
}
