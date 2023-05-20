package com.example.lpncounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dbLPNumbersRef, dbNumbersInLP, dbReachedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        database = FirebaseDatabase.getInstance();
        dbLPNumbersRef = database.getReference("LPNumbers");
        dbNumbersInLP = database.getReference("NumbersInLP");
        dbReachedNumber = database.getReference("ReachedNumber");

        dbNumbersInLP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<ArrayList<Integer>> t = new GenericTypeIndicator<ArrayList<Integer>>() {
                };
                ArrayList<Integer> numbersInLP = dataSnapshot.getValue(t);
                DataModel.NumbersInLP.clear();
                DataModel.NumbersInLP.addAll(numbersInLP);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("CSGuide", "Failed to read value.", error.toException());
            }
        });

        dbReachedNumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<Integer> t = new GenericTypeIndicator<Integer>() {
                };
                int reachedNumber = dataSnapshot.getValue(t);
                DataModel.ReachedNumber = reachedNumber;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("CSGuide", "Failed to read value.", error.toException());
            }
        });

        dbLPNumbersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<ArrayList<Integer>> t = new GenericTypeIndicator<ArrayList<Integer>>() {
                };
                ArrayList<Integer> fbUsers = dataSnapshot.getValue(t);
                DataModel.LPNumbers.clear();
                DataModel.LPNumbers.addAll(fbUsers);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("LPNCounter", "Failed to read value.", error.toException());
            }
        });
    }
}