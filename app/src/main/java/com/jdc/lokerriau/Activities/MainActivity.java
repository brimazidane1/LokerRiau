package com.jdc.lokerriau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jdc.lokerriau.R;

public class MainActivity extends AppCompatActivity {
    private Button openTeachersActivityBtn,openUploadActivityBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);

//        openTeachersActivityBtn = findViewById ( R.id.openTeachersActivityBtn );
//        openUploadActivityBtn = findViewById ( R.id.openUploadActivityBtn );
//
//        openTeachersActivityBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), ItemsActivity.class);
//                startActivity(i);
//            }
//        });
//        openUploadActivityBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), UploadActivity.class);
//                startActivity(i);
//            }
//        });

    }


}
