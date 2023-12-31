package com.example.runtimepermissionrequestkotlin

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var permisionLauncher: ActivityResultLauncher<Array<String>>
    private var isRecordAudioPermissionGranted = false;
    private var isCameraPermissionGranted = false;
    private var isSenSMSPermissionGranted = false;
    private var isReadSMSPermissionGrangted = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permisionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            permissions ->
            isRecordAudioPermissionGranted = permissions[Manifest.permission.RECORD_AUDIO]?:isRecordAudioPermissionGranted
            isCameraPermissionGranted = permissions[Manifest.permission.CAMERA]?:isCameraPermissionGranted
            isSenSMSPermissionGranted = permissions[Manifest.permission.SEND_SMS]?:isSenSMSPermissionGranted
            isReadSMSPermissionGrangted = permissions[Manifest.permission.READ_SMS]?:isReadSMSPermissionGrangted
        }
        requestPermission()
        println("ok")
    }
    private fun requestPermission(){
        isRecordAudioPermissionGranted = ContextCompat.checkSelfPermission(
            this,Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        isCameraPermissionGranted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        isSenSMSPermissionGranted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED

        isReadSMSPermissionGrangted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_SMS
        ) == PackageManager.PERMISSION_GRANTED

        val permissionRequest: MutableList<String> = ArrayList()
        if(!isRecordAudioPermissionGranted){
                permissionRequest.add(Manifest.permission.RECORD_AUDIO)
        }

        if(!isCameraPermissionGranted){
            permissionRequest.add(Manifest.permission.CAMERA)
        }

        if(!isSenSMSPermissionGranted){
            permissionRequest.add(Manifest.permission.SEND_SMS)
        }

        if(!isReadSMSPermissionGrangted){
            permissionRequest.add(Manifest.permission.READ_SMS)
        }


        if(permissionRequest.isNotEmpty()){
            permisionLauncher.launch(permissionRequest.toTypedArray())
        }

        println("ok")
    }

}