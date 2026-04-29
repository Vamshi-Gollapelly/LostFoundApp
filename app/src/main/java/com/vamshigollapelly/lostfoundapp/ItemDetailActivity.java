package com.vamshigollapelly.lostfoundapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        db     = new DatabaseHelper(this);
        itemId = getIntent().getIntExtra("item_id", -1);
        if (itemId == -1) { finish(); return; }

        LostFoundItem item = db.getItemById(itemId);
        if (item == null)  { finish(); return; }

        TextView tvType  = findViewById(R.id.tvDetailType);
        TextView tvName  = findViewById(R.id.tvDetailName);
        TextView tvPhone = findViewById(R.id.tvDetailPhone);
        TextView tvDesc  = findViewById(R.id.tvDetailDesc);
        TextView tvDate  = findViewById(R.id.tvDetailDate);
        TextView tvLoc   = findViewById(R.id.tvDetailLocation);
        TextView tvCat   = findViewById(R.id.tvDetailCategory);
        TextView tvStamp = findViewById(R.id.tvDetailTimestamp);
        ImageView ivImg  = findViewById(R.id.ivDetailImage);

        tvType.setText(item.getType());
        tvName.setText("Contact: "  + item.getName());
        tvPhone.setText("Phone: "   + item.getPhone());
        tvDesc.setText(item.getDescription());
        tvDate.setText("Date: "     + item.getDate());
        tvLoc.setText("Location: "  + item.getLocation());
        tvCat.setText("Category: "  + item.getCategory());
        tvStamp.setText("Posted: "  + item.getTimestamp());

        tvType.setBackgroundColor("Lost".equals(item.getType())
                ? 0xFFE74C3C : 0xFF27AE60);

        if (item.getImageUri() != null && !item.getImageUri().isEmpty())
            ivImg.setImageURI(Uri.parse(item.getImageUri()));

        Button btnRemove = findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(v -> confirmRemove());
    }

    private void confirmRemove() {
        new AlertDialog.Builder(this)
                .setTitle("Remove Advert")
                .setMessage("Has the item been found? Remove advert?")
                .setPositiveButton("Yes, Remove", (d, w) -> {
                    db.deleteItem(itemId);
                    Toast.makeText(this, "Advert removed",
                            Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}