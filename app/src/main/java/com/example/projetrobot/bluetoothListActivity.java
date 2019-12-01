package com.example.projetrobot;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import android.view.View;

import java.util.ArrayList;
import java.util.Set;

public class bluetoothListActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_list);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        listView = findViewById(R.id.listViewBT);;

        if(mBluetoothAdapter.isEnabled()){
            Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
            for(BluetoothDevice device: devices){
                mDeviceList.add(device.getName() + "\n" + device.getAddress());
            }
            listView.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, mDeviceList));

        }
        else{
            Toast.makeText(this, "Aucun appareil disponible. Veuillez connecter des appareils.", Toast.LENGTH_SHORT).show();
            finish();
        }

        // ListView on item selected listener.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedFromList =(String) (listView.getItemAtPosition(position));
                String selectedItem[] = selectedFromList.split("\\n");

                //Création de la nouvelle activité
                Intent intent = new Intent(bluetoothListActivity.this, RobotController.class);
                intent.putExtra("adrDevice", selectedItem[1]);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mDeviceList.add(device.getName() + "\n" + device.getAddress());
                Log.i("BT", device.getName() + "\n" + device.getAddress());
                listView.setAdapter(new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, mDeviceList));
            }
        }
    };

}
