package com.jdc.lokerriau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import com.jdc.lokerriau.Adapters.RecyclerAdapter;
import com.jdc.lokerriau.Activities.Lowongan;
import com.jdc.lokerriau.R;


public class ItemsActivity extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Lowongan> mLowongan;

    private void openDetailActivity(String[] data){
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("INSTANSI_KEY",data[0]);
        intent.putExtra("NAMA_KEY",data[1]);
        intent.putExtra("POSISI_KEY",data[2]);
        intent.putExtra("GAJI_KEY",data[3]);
        intent.putExtra("JENIS_KEY",data[4]);
        intent.putExtra("BUKA_KEY",data[5]);
        intent.putExtra("TUTUP_KEY",data[6]);
        intent.putExtra("KET_KEY",data[7]);
        intent.putExtra("IMAGE_KEY",data[8]);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_items );

        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressBar = findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        mLowongan = new ArrayList<> ();
        mAdapter = new RecyclerAdapter (ItemsActivity.this, mLowongan);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(ItemsActivity.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("lowongan");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mLowongan.clear();

                for (DataSnapshot teacherSnapshot : dataSnapshot.getChildren()) {
                    Lowongan upload = teacherSnapshot.getValue(Lowongan.class);
                    upload.setKey(teacherSnapshot.getKey());
                    mLowongan.add(upload);
                }
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ItemsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
    public void onItemClick(int position) {
        Lowongan clickedTeacher=mLowongan.get(position);
        String[] teacherData={clickedTeacher.getInstansi(), clickedTeacher.getName(), clickedTeacher.getPosisi(), clickedTeacher.getGaji(), clickedTeacher.getJenis(), clickedTeacher.getBuka(), clickedTeacher.getTutup(), clickedTeacher.getKet(),clickedTeacher.getImageURL()};
        openDetailActivity(teacherData);
    }

    @Override
    public void onShowItemClick(int position) {
        Lowongan clickedTeacher=mLowongan.get(position);
        String[] teacherData={clickedTeacher.getInstansi(), clickedTeacher.getName(), clickedTeacher.getPosisi(), clickedTeacher.getGaji(), clickedTeacher.getJenis(), clickedTeacher.getBuka(), clickedTeacher.getTutup(), clickedTeacher.getKet(),clickedTeacher.getImageURL()};
        openDetailActivity(teacherData);
    }

    @Override
    public void onDeleteItemClick(int position) {
        Lowongan selectedItem = mLowongan.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageURL());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(ItemsActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

}

