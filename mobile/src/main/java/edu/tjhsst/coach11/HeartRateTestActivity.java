package edu.tjhsst.coach11;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.security.KeyPairGenerator;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class HeartRateTestActivity extends ActionBarActivity {

    private TextView heartrate;
    private TextView current;
    public static final String APP_TAG = "SimpleHealth";
    private static HeartRateTestActivity mInstance = null;
    private HealthDataStore mStore;
    private HealthConnectionErrorResult mConnError;
    private Set<HealthPermissionManager.PermissionKey> mKeySet;

    private final HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult> mPermissionListener =
            new HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult>() {
                @Override
                public void onResult(HealthPermissionManager.PermissionResult result) {
                    Log. d ( APP_TAG , "Permission callback is received.");
                    Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = result.getResultMap();
                    if (resultMap.containsValue(Boolean. FALSE )) {
                        // Requesting permission fails
                    } else {
                        // Get the current step count and display it
                        String u = HealthConstants.HeartRate.HEART_BEAT_COUNT;
                        heartrate.setText(u);
                    }
                }
            };

    private final HealthDataStore.ConnectionListener mConnectionListener = new HealthDataStore.ConnectionListener() {
        @Override
        public void onConnected() {
            Log.d(APP_TAG, "Health service connected.");
            HealthPermissionManager pmsManager = new HealthPermissionManager(mStore); ////*LEFT OFF HERE
            try {
                Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(mKeySet);
                if (resultMap.containsValue(Boolean. FALSE )) {
                        // Request the permission for reading step counts if it is not acquired
                    pmsManager.requestPermissions(mKeySet, HeartRateTestActivity.this).setResultListener(mPermissionListener);
                } else {
                    // Get the current step count and display it
                        // ...
                }
            }catch (Exception e) {
                    Log.e(APP_TAG, e.getClass().getName() + " - " + e.getMessage());
                Log.e(APP_TAG, "Permission setting fails.");
                }
        }


        @Override
        public void onConnectionFailed(HealthConnectionErrorResult error) {
            Log.d(APP_TAG, "HEALTH data service is not available");
            showConnectionFailureDialog(error);
        }

        @Override
        public void onDisconnected() {
            Log.d(APP_TAG, "HEALTH data service is disconnected. ");
        }
        private void showConnectionFailureDialog(HealthConnectionErrorResult error) {
            // HERE
           AlertDialog.Builder alert = new AlertDialog.Builder(HeartRateTestActivity.this);
            mConnError = error;
            String message = "Connection with S Health is not available";
            if (mConnError.hasResolution()) {
                switch (error.getErrorCode()) {
                    case HealthConnectionErrorResult.PLATFORM_NOT_INSTALLED:
                        message = "Please install S Health";
                        break;
                    case HealthConnectionErrorResult.OLD_VERSION_PLATFORM:
                        message = "Please upgrade S Health";
                        break;
                    case HealthConnectionErrorResult.PLATFORM_DISABLED:
                        message = "Please enable S Health";
                        break;
                    case HealthConnectionErrorResult.USER_AGREEMENT_NEEDED:
                        message = "Please agree with S Health policy";
                        break;
                    default:
                        message = "Please make S Health available";
                        break;
                }

            }
            alert.setMessage(message);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    if (mConnError.hasResolution()) {
                        mConnError.resolve(mInstance);
                    }
                }
            });
            if (error.hasResolution()) {
                alert.setNegativeButton("Cancel", null);
            }
            alert.show();
        }
    };


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_test);
        mInstance = this;
        heartrate = (TextView) findViewById(R.id.instantHearRate); //sets heartrate = to the label; will be used to update
        current = (TextView) findViewById(R.id.current);
        changeNumber(""+55);

        mKeySet = new HashSet<HealthPermissionManager.PermissionKey>();
        mKeySet.add(new HealthPermissionManager.PermissionKey(HealthConstants.StepCount.HEALTH_DATA_TYPE, HealthPermissionManager.PermissionType.READ));
        try{
            KeyPairGenerator healthDataService = null;
            healthDataService.initialize((AlgorithmParameterSpec) this);
        }catch(Exception e){
            e.printStackTrace();
        }
        // if succeeds connect to health data store ,... create an instance of it and set its listener
        HealthDataStore.ConnectionListener mConnectionListener = null;
        mKeySet.add(new HealthPermissionManager.PermissionKey(HealthConstants.StepCount. HEALTH_DATA_TYPE , HealthPermissionManager.PermissionType. READ ));
        HealthDataService healthDataService = new HealthDataService();
        mStore = new HealthDataStore(this,mConnectionListener);
        mStore.connectService();
    }
    public void changeNumber(String b)
    {
        current.setText(b);
        return;
    }
    @Override
    public void onDestroy() // can end health data store connection when acitivity is destroyed//
    {
    mStore.disconnectService();
        super.onDestroy();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_heart_rate_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
