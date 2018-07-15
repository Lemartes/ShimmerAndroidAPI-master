package com.kamil.shimmercontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
    Shimmer shimmer;

    TableLayout text;
    ConstraintLayout data;
    Button calibrate;
    Button button2;
    Button button3;
    Button connect;
    ToggleButton enable;
    TextView enable2;
    TextView connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shimmer = new Shimmer(mHandler);
        calibrate = (Button) findViewById(R.id.calibration);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        connect = (Button) findViewById(R.id.connect);
        enable = (ToggleButton) findViewById(R.id.enable);
        enable2 = (TextView) findViewById(R.id.enableText);
        connection = (TextView) findViewById(R.id.connected);
        text = (TableLayout) findViewById(R.id.ttsTXT);
        data = (ConstraintLayout) findViewById(R.id.data);
    }

    public void data(View view) {
        text.setVisibility(View.INVISIBLE);
        data.setVisibility(View.VISIBLE);
    }

    public void ttsshow(View view) {
        text.setVisibility(View.VISIBLE);
        data.setVisibility(View.INVISIBLE);
    }

    public void connect(View view) {

        Intent intent = new Intent(getApplicationContext(), ShimmerBluetoothDialog.class);
        startActivityForResult(intent, ShimmerBluetoothDialog.REQUEST_CONNECT_SHIMMER);

        if (shimmer.isConnected()) {
            shimmer.startStreaming();
            startTime = System.currentTimeMillis();
        }
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ShimmerBluetooth.MSG_IDENTIFIER_DATA_PACKET:
                    if ((msg.obj instanceof ObjectCluster)) {

                        ObjectCluster objectCluster = (ObjectCluster) msg.obj;

                        Collection<FormatCluster> allFormats = objectCluster.getCollectionOfFormatClusters(Configuration.Shimmer3.ObjectClusterSensorName.MAG_X);
                        FormatCluster accelX = ((FormatCluster) ObjectCluster.returnFormatCluster(allFormats, "CAL"));
                        Collection<FormatCluster> allFormats2 = objectCluster.getCollectionOfFormatClusters(Configuration.Shimmer3.ObjectClusterSensorName.MAG_Y);
                        FormatCluster accelY = ((FormatCluster) ObjectCluster.returnFormatCluster(allFormats2, "CAL"));
                        Collection<FormatCluster> allFormats3 = objectCluster.getCollectionOfFormatClusters(Configuration.Shimmer3.ObjectClusterSensorName.MAG_Z);
                        FormatCluster accelZ = ((FormatCluster) ObjectCluster.returnFormatCluster(allFormats3, "CAL"));
                        if (accelXCluster != null) {
                            double accelX = accelXCluster.mData;
                            double accelY = accelYCluster.mData;
                            if (accelY < 0.0) {
                                Log.i(LOG_TAG, "LEWO");
                            }
                            if (accelY > 0.75) {
                                Log.i(LOG_TAG, "PRAWO");
                            }
                            if (accelX > -0.1) {
                                Log.i(LOG_TAG, "GÓRA");
                            }
                            if (accelX < -0.6) {
                                Log.i(LOG_TAG, "DÓł");
                            }
                            if (accelZ > -0.1) {
                                Log.i(LOG_TAG, "PRZÓD");
                            }
                            if (accelZ < -0.6) {
                                Log.i(LOG_TAG, "TYŁ");
                            }


                        }

                    }
                    break;
                case Shimmer.MESSAGE_TOAST:
                    /** Toast messages sent from {@link Shimmer} are received here. E.g. device xxxx now streaming.
                     *  Note that display of these Toast messages is done automatically in the Handler in {@link com.shimmerresearch.android.shimmerService.ShimmerService} */
                    Toast.makeText(getApplicationContext(), msg.getData().getString(Shimmer.TOAST), Toast.LENGTH_SHORT).show();
                    break;
                case ShimmerBluetooth.MSG_IDENTIFIER_STATE_CHANGE:
                    ShimmerBluetooth.BT_STATE state = null;
                    String macAddress = "";

                    if (msg.obj instanceof ObjectCluster) {
                        state = ((ObjectCluster) msg.obj).mState;
                        macAddress = ((ObjectCluster) msg.obj).getMacAddress();
                    } else if (msg.obj instanceof CallbackObject) {
                        state = ((CallbackObject) msg.obj).mState;
                        macAddress = ((CallbackObject) msg.obj).mBluetoothAddress;
                    }

                    switch (state) {
                        case CONNECTED:
                            break;
                        case CONNECTING:
                            break;
                        case STREAMING:
                            break;
                        case STREAMING_AND_SDLOGGING:
                            break;
                        case SDLOGGING:
                            break;
                        case DISCONNECTED:
                            break;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };


}
