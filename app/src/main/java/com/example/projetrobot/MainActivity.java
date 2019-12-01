package com.example.projetrobot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToBluetoothListActivity(View view){
        Button btn = (Button) findViewById(R.id.btnConnectBT);
        Toast.makeText(this,(String)btn.getText(),Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent (this, bluetoothListActivity.class);
        startActivity(intent);*/
    }
}
