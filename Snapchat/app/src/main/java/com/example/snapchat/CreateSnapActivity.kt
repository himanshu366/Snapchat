 package com.example.snapchat

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
//import java.util.jar.Manifest
 import android.Manifest
import android.app.Activity
import java.lang.Exception

 class CreateSnapActivity : AppCompatActivity() {

    var createSnapImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)
    }

     fun getPhoto() {
         val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
         startActivityForResult(intent, 1)
     }

     fun chooseImageClicked(view: View) {
         if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
             requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)

     } else {
         getPhoto()
         }
     }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)

         val seletedImage = data!!.data

         if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
             try {
                 val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, seletedImage)
                 createSnapImageView?.setImageBitmap(bitmap)
             } catch (e: Exception) {
                 e.printStackTrace()
             }
         }
     }

     override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)

         if (requestCode == 1) {
             if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 getPhoto()
             }
         }
     }

}
