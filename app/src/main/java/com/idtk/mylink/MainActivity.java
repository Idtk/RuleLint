package com.idtk.mylink;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "toast", Toast.LENGTH_SHORT);

        Log.d("tag", "msg");
        ABean a =  new ABean();
        a.setName("111");

    }


}
