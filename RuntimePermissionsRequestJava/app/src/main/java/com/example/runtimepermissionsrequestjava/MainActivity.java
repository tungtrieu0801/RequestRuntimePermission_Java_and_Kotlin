package com.example.runtimepermissionsrequestjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    private static final int PERMISSION_REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(
                v -> requestRuntimePermission()
        );
    }

    private void requestRuntimePermission() {
        if (ActivityCompat.checkSelfPermission(this, PERMISSION_RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_RECORD_AUDIO)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This app requires RECORD_AUDIO Permission to do sthg")
                    .setTitle("Permission required")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{PERMISSION_RECORD_AUDIO}, PERMISSION_REQ_CODE);
                dialogInterface.dismiss();
            }).setNegativeButton("Cancel", ((dialogInterface, i) -> dialogInterface.dismiss()));
            builder.show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION_RECORD_AUDIO}, PERMISSION_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_RECORD_AUDIO)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("This featue is unvaliverble because you deny ")
                        .setTitle("Permission requried").setCancelable(false)
                        .setNegativeButton("Cancel", ((dialogInterface, i) -> dialogInterface.dismiss()))
                        .setPositiveButton("Settings", (dialogInterface, i) -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);

                            dialogInterface.dismiss();
                        });
                builder.show();
            }else{
                requestRuntimePermission();
            }
        }
    }
}