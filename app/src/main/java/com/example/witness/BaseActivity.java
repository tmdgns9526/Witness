package com.example.witness;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    public void setContentView(int layoutResID) {
        LinearLayout fullView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);

        Toolbar toolbar = findViewById(R.id.toolbar);

        if(useToolbar()) {
            setSupportActionBar(toolbar);
            setTitle("");
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }

    protected boolean useToolbar() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAlbum:
                Toast.makeText(BaseActivity.this, "앨범기능", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemMap:
                Toast.makeText(BaseActivity.this, "지도기능", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemNotice:
                Toast.makeText(BaseActivity.this, "알림기능", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemMore:
                Toast.makeText(BaseActivity.this, "더보기기능", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return true;
        }
    }
}
