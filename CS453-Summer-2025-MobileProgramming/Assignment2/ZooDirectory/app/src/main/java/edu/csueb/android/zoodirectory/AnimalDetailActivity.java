package edu.csueb.android.zoodirectory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AnimalDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);

        TextView nameView = findViewById(R.id.detailName);
        ImageView imageView = findViewById(R.id.detailImage);
        TextView descriptionView = findViewById(R.id.detailDescription);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        int imageId = intent.getIntExtra("imageId", 0);

        nameView.setText(name);
        imageView.setImageResource(imageId);
        descriptionView.setText(description);
    }
}