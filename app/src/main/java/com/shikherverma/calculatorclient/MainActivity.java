package com.shikherverma.calculatorclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
  public static String LOG = MainActivity.class.toString();
  public static String URL = "http://adarshaj.cse.iitk.ac.in/operation.php/";
  public static String REQUEST_TAG = "REQUEST_TAG";
  EditText mInputOne;
  EditText mInputTwo;
  Spinner mOperator;
  Button mCalculate;
  TextView mResult;
  private RequestQueue mRequestQueue;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.i(LOG, "onCreate started");
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    mInputOne = (EditText) findViewById(R.id.input_one);
    mOperator = (Spinner) findViewById(R.id.spinner);
    mInputTwo = (EditText) findViewById(R.id.input_two);
    mCalculate = (Button) findViewById(R.id.button);
    mResult = (TextView) findViewById(R.id.result);
    Log.i(LOG, "got reference to all views");
  }

  public void onButtonClick(View v) {
    Log.i(LOG, "Pressed Calculate");
    if (mInputOne.getText().toString().isEmpty() || mInputTwo.getText().toString().isEmpty()) {
      mResult.setText("Input something first");
      return;
    }
    double operandOne = Double.parseDouble(mInputOne.getText().toString());
    double operandTwo = Double.parseDouble(mInputTwo.getText().toString());
    String operator = mOperator.getSelectedItem().toString();
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("operand_1", operandOne);
      jsonObject.put("operand_2", operandTwo);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    Log.i(LOG, "jsonObject " + jsonObject.toString());
    Log.i(LOG, "url " + URL + operator);
    JsonRequest request = new JsonObjectRequest(Request.Method.POST, URL + operator, jsonObject.toString(), new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        try {
          String result = response.get("result").toString();
          mResult.setText(result);
        } catch (JSONException e) {
          e.printStackTrace();
          mResult.setText("Error! parsing the response!");
        }
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        if (error instanceof NoConnectionError) //can't connect to the internet
        {
          mResult.setText("Error! can't connect to the internet!");
        } else //connected to internet but couldn't connect to server!!
        {
          mResult.setText("Error! Server revolted!");
        }
      }
    });
    request.addMarker(REQUEST_TAG);
    mRequestQueue.add(request);
    Log.i(LOG, "Made network request");
    mResult.setText("calculating...");
  }
}
