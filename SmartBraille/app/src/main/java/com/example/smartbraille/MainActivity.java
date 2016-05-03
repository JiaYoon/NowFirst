package com.example.smartbraille;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText et;
    Button bt;
    TextView tv;
    Braille braille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText)findViewById(R.id.edit);
        bt = (Button)findViewById(R.id.convert);
        tv = (TextView)findViewById(R.id.text);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et.getText().toString();
                braille = new Braille(str);
                Log.d("MainActivity", braille.getString());
                tv.setText(braille.getString());
            }
        });
    }
}
