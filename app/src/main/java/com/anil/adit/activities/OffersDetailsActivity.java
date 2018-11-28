package com.anil.adit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.anil.adit.R;

public class OffersDetailsActivity extends AppCompatActivity {
    private TextView cName,desc;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shops");

        cName=(TextView)findViewById(R.id.company_name);
        desc=(TextView)findViewById(R.id.desc);
        imageView=(ImageView) findViewById(R.id.imageView);

        cName.setText(getIntent().getExtras().getString("cName"));
        desc.setText(getIntent().getExtras().getString("desc"));
        imageView.setImageResource(getIntent().getExtras().getInt("img"));
    }
    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        super.onBackPressed();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
