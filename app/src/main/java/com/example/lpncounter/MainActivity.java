package com.example.lpncounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText LPNumber;
    Button addItem;
    TextView reachedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reachedNumber = findViewById(R.id.reachedNumberTV);
        reachedNumber.setText(Integer.toString(DataModel.ReachedNumber));

        LPNumber = findViewById(R.id.LPNumber);
        addItem = findViewById(R.id.addBtn);

        addItem.setOnClickListener(this);
    }

    private void updateReachedNumber()
    {
        while(true)
        {
            if(DataModel.NumbersInLP.contains(DataModel.ReachedNumber + 1))
                DataModel.ReachedNumber++;
            else
                break;
        }
        DataModel.ReachedNumberSave();
    }

    private void addNumbersInLP(String LPN)
    {
        ArrayList<Integer> numbersInNewLP = new ArrayList<>();
        String[] digits = LPN.split("");

        ArrayList<String> tempArr = new ArrayList<>();

        int LPNLength = digits.length;
        for (int i=0; i < LPNLength; i++)
        {
            // Pick ending point
            for (int j=i; j < LPNLength; j++)
            {
                // and ending points
                for (int k=i; k<=j; k++)
                    tempArr.add(digits[k]);

                String newLPNumber = TextUtils.join("", tempArr);
                if(newLPNumber != "" && newLPNumber.length < 5)
                {
                    numbersInNewLP.add(Integer.parseInt(newLPNumber));
                }
                tempArr.clear();
            }
        }

        for(int number : numbersInNewLP)
        {
            if(!DataModel.NumbersInLP.contains(number))
                DataModel.NumbersInLP.add(number);
        }
        DataModel.NumbersInLPSave();
    }

    @Override
    public void onClick(View v) {
        if(v == addItem)
        {
            String lPNumberStr = LPNumber.getText().toString();
            if(!DataModel.LPNumbers.contains(lPNumberStr))
            {
                try {
                    int newLPNumber = Integer.parseInt(lPNumberStr);
                    DataModel.LPNumbers.add(newLPNumber);
                    DataModel.LPNumbersSave();
                    addNumbersInLP(lPNumberStr);
                    updateReachedNumber();
                } catch (NumberFormatException e){
                }
            }
            restartapp();
        }
    }

    void restartapp() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}