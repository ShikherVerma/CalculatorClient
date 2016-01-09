package com.shikherverma.calculatorclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText mInputOne;
    EditText mInputTwo;
    Spinner mOperator;
    Button mCalculate;
    TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mInputOne = (EditText) findViewById(R.id.input_one);
        mOperator = (Spinner) findViewById(R.id.spinner);
        mInputTwo = (EditText) findViewById(R.id.input_two);
        mCalculate = (Button) findViewById(R.id.button);
        mResult = (TextView) findViewById(R.id.result);
    }

    public void onButtonClick(View v)
    {
        double operandOne = Integer.parseInt(mInputOne.getText().toString());
        double operandTwo = Integer.parseInt(mInputTwo.getText().toString());
        String operator = mOperator.getSelectedItem().toString();
        String result = calculate(operandOne, operandTwo, operator);
        mResult.setText(result);
    }

    public String calculate(double x, double y, String operator)
    {
        Random random = new Random();
        ArrayList<String> list = new ArrayList<String>();
        list.add("May be 0");
        list.add("42");
        list.add("666");
        list.add("Do it yourself");
        return list.get(random.nextInt(list.size()));
    }
}
