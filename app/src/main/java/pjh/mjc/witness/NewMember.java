package pjh.mjc.witness;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewMember extends AppCompatActivity {
    ImageButton btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_member);

        Spinner spinner = findViewById(R.id.domain);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.domain, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
