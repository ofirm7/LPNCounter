package com.example.lpncounter;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class DataModel {
    static public ArrayList<Integer> LPNumbers = new ArrayList<>();
    static public ArrayList<Integer> NumbersInLP = new ArrayList<>();
    static public int ReachedNumber = 0;

    public static void LPNumbersSave()
    {
        FirebaseDatabase.getInstance().getReference("LPNumbers").setValue(DataModel.LPNumbers);
    }

    public static void NumbersInLPSave()
    {
        FirebaseDatabase.getInstance().getReference("NumbersInLP").setValue(DataModel.NumbersInLP);
    }

    public static void ReachedNumberSave()
    {
        FirebaseDatabase.getInstance().getReference("ReachedNumber").setValue(DataModel.ReachedNumber);
    }
}