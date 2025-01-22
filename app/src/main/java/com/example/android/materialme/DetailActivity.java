package com.example.android.materialme;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitleText;
    private ImageView mImageView;
    private TextView mInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize the views
        mTitleText = findViewById(R.id.titleDetail);
        mImageView = findViewById(R.id.sportsImageDetail);
        mInfoText = findViewById(R.id.subTitleDetail);

        // Get the data passed from the Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("sport_title");
        String info = intent.getStringExtra("sport_info");

        // Set the title in the TextView
        mTitleText.setText(title);
        mInfoText.setText(info);
        Glide.with(this).load(getIntent().getIntExtra("sport_image",0))
                .into(mImageView);



    }
}
