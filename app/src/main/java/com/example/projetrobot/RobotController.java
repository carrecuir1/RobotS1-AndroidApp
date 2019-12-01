package com.example.projetrobot;

import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.UUID;

public class RobotController extends AppCompatActivity {

    Button btnMotor, btnGauche, btnDroite, btnTirer, btnDis;
    String adrDevice = null;
    private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Peu être problématique
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    Timer timer = new Timer();
    int startSend=0;
    String Bm="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_controller);

        btnMotor=(Button)findViewById(R.id.btnMotor);
        btnGauche=(Button)findViewById(R.id.btnGauche);
        btnDroite=(Button)findViewById(R.id.btnDroite);
        btnTirer=(Button)findViewById(R.id.btnTirer);
        btnDis=(Button)findViewById(R.id.btnDis);

        adrDevice = getIntent().getStringExtra("adrDevice");
        if(adrDevice == null){
            Toast.makeText(this,"La connection a échoué. Veuillez réessayer.", Toast.LENGTH_SHORT).show();
            finish();
        }
        ConnectBT();
    }
    /********************TODO LIST
     * Renommer les noms des boutons
     * Faire ne sorte que le timer fonctionne
     * Envoyer des infos en permanance au BT
     * *********************/
/*
    timer.schedule(new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(startSend==1) SendBluetooth(Bm);
                }
            });

        }
    }, 0, 50);

    startSend=1;

    //commands to be sent to bluetooth
        btnForward.setOnTouchListener (new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Bm="1";
                    break;
                case MotionEvent.ACTION_UP:
                    Bm="0";
                    break;
            }
            return false;
        }
    });

        btnLeft.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Bm = "2";
                    break;
                case MotionEvent.ACTION_UP:
                    Bm = "0";
                    break;
            }
            return false;
        }
    });

        btnBack.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Bm = "3";
                    break;
                case MotionEvent.ACTION_UP:
                    Bm = "0";
                    break;
            }
            return false;
        }
    });

        btnRight.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Bm = "4";
                    break;
                case MotionEvent.ACTION_UP:
                    Bm = "0";
                    break;
            }
            return false;
        }
    });

        btnDis.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Disconnect(); //close connection
        }
    });
}

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            {
                //msg("Error");
            }
        }
        finish(); //return to the first layout

    }
*/
    private Boolean ConnectBT(){
        Boolean success = true;
        try
        {
            if (btSocket == null)
            {
                myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                BluetoothDevice device = myBluetooth.getRemoteDevice(adrDevice);//connects to the device's address and checks if it's available

                btSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID);
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                btSocket.connect();//start connection
                Toast.makeText(this,"Connection réussi", Toast.LENGTH_SHORT).show();
            }
        }
        catch (IOException e)
        {
            success = false;//if the try failed, you can check the exception here
        }
        return success;
    }

}
