package com.example.happyshop.storage

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.example.happyshop.ui.activities.ProductActivity
import com.example.happyshop.ui.activities.UserProfileActivity

class FirebaseStorage {
    val firebaseStorage=com.google.firebase.storage.FirebaseStorage.getInstance()
    fun uploadImageToStorage(activity: Activity,imageUri: Uri?,imageType:String){

          val imageExtension = MimeTypeMap.getSingleton()
              .getExtensionFromMimeType(activity.contentResolver.getType(imageUri!!))


          val sRef = firebaseStorage.reference.child(
              imageType + System.currentTimeMillis() + ". " + imageExtension
          )
          sRef.putFile(imageUri!!).addOnSuccessListener{
                  taskSnapshot ->

              taskSnapshot.metadata!!.reference!!.downloadUrl
                  .addOnSuccessListener {
                      uri->
                      when(activity){
                          is UserProfileActivity ->{
                              activity.imageUploadSuccess(uri.toString())

                          }
                        is ProductActivity ->{
                            activity.imageUploadSuccess(uri.toString())


                        }
                      }

                  }.addOnFailureListener {
                      Toast.makeText(activity,it.message.toString(),Toast.LENGTH_LONG).show()
                  }

          }.addOnFailureListener{
              e->
              Log.e("File Not Uploaded",e.message.toString()
              )
          }


      }
    }



