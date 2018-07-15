package com.kamil.shimmercontrol;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends Activity {
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    public ArrayList<BluetoothDevice> BTdevices = new ArrayList<>();
    public DeviceListAdapter mDeviceListAdapter;
    BluetoothAdapter bluetoothAdapter;
    ListView lvNewDevices;
    BluetoothDevice BTdevice;

    TableLayout text;
    ConstraintLayout data;
    Button calibrate;
    Button button2;
    Button button3;
    Button connect;
    ToggleButton enable;
    TextView enable2;


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                BTdevices.add(device);
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, BTdevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TableLayout) findViewById(R.id.ttsTXT);
        data = (ConstraintLayout) findViewById(R.id.data);
    }

    public void data(View view){
        text.setVisibility(View.INVISIBLE);
        data.setVisibility(View.VISIBLE);
    }

    public void ttsshow(View view){
        text.setVisibility(View.VISIBLE);
        data.setVisibility(View.INVISIBLE);
    }

    public void connect(View view) {

        /*connect.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);
        data.setVisibility(View.INVISIBLE);
        calibrate.setVisibility(View.INVISIBLE);
        enable.setVisibility(View.INVISIBLE);
        enable2.setVisibility(View.INVISIBLE);

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
            bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(broadcastReceiver, discoverDevicesIntent);
        }
        if (!bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(broadcastReceiver, discoverDevicesIntent);
        }

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        BTdevices = new ArrayList<>();
        lvNewDevices = (ListView) findViewById(R.id.NewDevices);
        lvNewDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                bluetoothAdapter.cancelDiscovery();
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    BTdevices.get(position).createBond();
                    BTdevice = BTdevices.get(position);
                }

                Shimmer

                connect.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
                button3.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
                data.setVisibility(View.VISIBLE);
                calibrate.setVisibility(View.VISIBLE);
                enable.setVisibility(View.VISIBLE);
                enable2.setVisibility(View.VISIBLE);

                BTdevices.clear();*/

        Intent intent = new Intent(getApplicationContext(), ShimmerBluetoothDialog.class);
        startActivityForResult(intent, ShimmerBluetoothDialog.REQUEST_CONNECT_SHIMMER);



            }
        });

    }
}
