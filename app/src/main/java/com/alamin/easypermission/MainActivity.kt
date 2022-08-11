package com.alamin.easypermission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import java.util.jar.Manifest
const val REQUEST_CODE = 999

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    lateinit var text: TextView
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text = findViewById(R.id.txtPermission);
        button = findViewById(R.id.btnPermission)

        button.setOnClickListener {
            getLocation()
        }

    }

    private fun hasPermission() = EasyPermissions.hasPermissions(this,android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION)

    private fun requestPermission(){
        EasyPermissions.requestPermissions(this,
            "This Application Needs Permission",
            REQUEST_CODE,android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms.subList(0,1))){
            SettingsDialog.Builder(this).build().show()
        }else{
            requestPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        getLocation();
    }

    private fun getLocation() {
        if (hasPermission()){
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            text.visibility = View.VISIBLE
            button.visibility = View.GONE
        }else{
            requestPermission()
        }
    }

    override fun onRestart() {
        super.onRestart()
        requestPermission()
    }
}