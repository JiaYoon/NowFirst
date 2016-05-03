package com.example.smartbraille;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static final int MY_PERMISSIONS_REQUEST_READ_EXTERN_STORAGE = 1;
    static final int PREV = -1;
    static final int NEXT = 1;

    String SDPATH;

    EditText et;
    TextView tv;
    Braille braille;
    FilePointer fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", isExternalStorageWritable() + "");
        Log.d("MainActivity", isExternalStorageReadable() + "");
        SDPATH = Environment.getExternalStorageDirectory().getPath();

        //android 4.4 이상 부터는 manifest 뿐만 아니라 runtime permission을 받아야함
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                //thread
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERN_STORAGE);
                //callback
            }
        }

        try {
            fp = new FilePointer(SDPATH+"/myEbook/", "aaa.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        et = (EditText)findViewById(R.id.edit);
        tv = (TextView)findViewById(R.id.text);
        braille = new Braille();
    }

    @Override
    protected void onDestroy (){
        try {
            fp.closeFilePointer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void myOnClick(View v) {
        String str = "";

        switch (v.getId()) {
            case R.id.next:
                try {
                    str = braille.toBraille(fp.readFile(NEXT));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d("MainActivity", str);
                Log.d("MainActivity", fp.getNowStr());
                tv.setText(str);

                break;
            case R.id.prev:
                try {
                    str = braille.toBraille(fp.readFile(PREV));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d("MainActivity", str);
                Log.d("MainActivity", fp.getNowStr());

                tv.setText(str);
                break;
            case R.id.init:
                fp.initFilePointer();
                try {
                    str = braille.toBraille(fp.readFile(NEXT));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("MainActivity", str);
                Log.d("MainActivity", fp.getNowStr());

                tv.setText(str);
                break;

        }

    }



    /* Checks if external storage is available for read and write */
    boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    //permission을 얻었을 때의 Callback function
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERN_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "granted", Toast.LENGTH_LONG).show();
                    //dialog는 thread로 돌기 때문에
                    //fp는 EACCESS로 인해 NULL이 들어가있음
                    try {
                        fp = new FilePointer(SDPATH+"/myEbook/", "aaa.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "not granted", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

}
