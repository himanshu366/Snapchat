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
import android.content.res.Resources
import kotlinx.android.synthetic.main.activity_create_snap.*
import java.lang.Exception
import com.google.android.gms.tasks.OnSuccessListener
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
//import com.google.android.gms.common.util.IOUtils.toByteArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
//import sun.jvm.hotspot.utilities.IntArray
import java.io.ByteArrayOutputStream
import java.util.*


private val UploadTask.TaskSnapshot.downloadUrl: Any
     get() { return 1 }

 class CreateSnapActivity : AppCompatActivity() {

    var createSnapImageView: ImageView? = null
     var messageEditText: EditText? = null
     val imageName = UUID.randomUUID().toString() + ".jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)

        createSnapImageView = findViewById(R.id.createSnapImageView)
        messageEditText = findViewById(R.id.messageEditText)
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

     fun nextClicked(view: View) {

         //createSnapImageView?.buildDrawingCache()

         var bitmap1: Bitmap = BitmapFactory.decodeFile("/storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20200331-WA0000.jpg");
         //val bitmap = (createSnapImageView?.getDrawable() as BitmapDrawable).bitmap
         val baos = ByteArrayOutputStream()
         bitmap1?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
         val data = baos.toByteArray()


         val uploadTask = FirebaseStorage.getInstance().getReference().child("Images").child(imageName).putBytes(data)
         uploadTask.addOnFailureListener(OnFailureListener {
             // Handle unsuccessful uploads
             Toast.makeText(this, "UPLOAD FAILED", Toast.LENGTH_SHORT).show()
         }).addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
             val downloadUrl = taskSnapshot.downloadUrl
             Log.i("URL", downloadUrl.toString())
         })
     }

}
