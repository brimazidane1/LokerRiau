package com.jdc.lokerriau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.jdc.lokerriau.R;

public class DetailsActivity extends AppCompatActivity {

    TextView instansiDetailTextView, nameDetailTextView, posisiDetailTextView, gajiDetailTextView, jenisDetailTextView, bukaDetailTextView, tutupDetailTextView, ketDetailTextView, dateDetailTextView;
    ImageView teacherDetailImageView;

    private void initializeWidgets(){
        instansiDetailTextView= findViewById(R.id.instansiDetailTextView);
        nameDetailTextView= findViewById(R.id.nameDetailTextView);
        posisiDetailTextView= findViewById(R.id.posisiDetailTextView);
        gajiDetailTextView= findViewById(R.id.gajiDetailTextView);
        jenisDetailTextView=findViewById(R.id.jenisDetailTextView);
        bukaDetailTextView=findViewById(R.id.bukaDetailTextView);
        tutupDetailTextView=findViewById(R.id.tutupDetailTextView);
        dateDetailTextView= findViewById(R.id.dateDetailTextView);
        ketDetailTextView= findViewById(R.id.ketDetailTextView);
        teacherDetailImageView=findViewById(R.id.lowonganDetailImageView);
    }
    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeWidgets();

        //RECEIVE DATA FROM ITEMSACTIVITY VIA INTENT
        Intent i=this.getIntent();
        String instansi=i.getExtras().getString("INSTANSI_KEY");
        String name=i.getExtras().getString("NAME_KEY");
        String posisi=i.getExtras().getString("POSISI_KEY");
        String gaji=i.getExtras().getString("GAJI_KEY");
        String jenis=i.getExtras().getString("JENIS_KEY");
        String buka=i.getExtras().getString("BUKA_KEY");
        String tutup=i.getExtras().getString("TUTUP_KEY");
        String ket=i.getExtras().getString("KET_KEY");
        String imageURL=i.getExtras().getString("IMAGE_KEY");

        //SET RECEIVED DATA TO TEXTVIEWS AND IMAGEVIEWS
        instansiDetailTextView.setText(instansi);
        nameDetailTextView.setText(name);
        posisiDetailTextView.setText(posisi);
        gajiDetailTextView.setText(gaji);
        jenisDetailTextView.setText(jenis);
        bukaDetailTextView.setText(buka);
        tutupDetailTextView.setText(tutup);
        ketDetailTextView.setText(ket);
        dateDetailTextView.setText("DATE: "+getDateToday());
        Picasso.with(this)
                .load(imageURL)
//                .placeholder(R.drawable.placeholder)
                .into(teacherDetailImageView);
//                .fit()
//                .centerCrop();

    }

}
