package com.example.happyshop.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.happyshop.model.Products
import com.example.happyshop.model.User
import com.example.happyshop.ui.activities.*
import com.example.happyshop.ui.fragments.DashboardFragment
import com.example.happyshop.ui.fragments.ProductFragment
import com.example.happyshop.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class Firestore {
    private val fireStore =FirebaseFirestore.getInstance()
//    private lateinit var mSharedPreference:SharedPreferences
private lateinit var mUser:User
    fun registerUserToDB(userInfo:User){
     fireStore.collection(Constants.USERS)
         .document(userInfo.id)
         .set(userInfo, SetOptions.merge())
         .addOnFailureListener {
             e->
             Log.e("Db Insertion","Error while Registering The User",e)
         }



    }

    fun registerProductsToDb(activity: ProductActivity,productInfo:Products){
        fireStore.collection(Constants.PRODUCTS)
            .document()
            .set(productInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.productUploadSuccess()
              Log.e("Product Insertion","Product Updated To Db")
            }
            .addOnFailureListener {
                e->
                Log.e("Product Insertion","Product Insertion Failed")
            }


    }

//Getting Current User Id
    fun getCurrentUserId():String{
        var currentUserId =""
        val currentUser =FirebaseAuth.getInstance().currentUser
        if (currentUser!=null){
            currentUserId=currentUser.uid
        }
        return currentUserId
    }
//Getting data from DataBase
    fun getCurrentUserDetails(activity: Activity){
        fireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {
                document->
                mUser= document.toObject(User::class.java)!!


                val sharedPreference= activity.getSharedPreferences(Constants.HAPPY_SHOP_PREFERENCE,Context.MODE_PRIVATE)
                val editor =sharedPreference.edit()
                editor.putString(Constants.LOGGED_IN_USERNAME, mUser.firstName)
                editor.putString(Constants.LOGGED_IN_SURNAME, mUser.lastName)
                editor.putString(Constants.USER_EMAIL, mUser.email)
                editor.apply()

                when(activity){
                    is LoginActivity ->{
                        activity.getUser(mUser)
                    }
                    is SettingActivity->{
                       activity.userDetailsSuccess(mUser)
                    }
                }

            }


            .addOnFailureListener {
                e->
                Log.e("Retrieval From Db","Error in Data Retrieval",e)

            }


    }
    fun updateUser(activity: Activity,userHashMap: HashMap<String,Any>){
        fireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                when(activity){
                    is UserProfileActivity ->{
                     activity.updateUserSuccess()
                }
                }


            }
            .addOnFailureListener {
                e->
                Log.e("Error Update to Db","Failed to update to FireStore",e)

            }


    }
     fun getProductListFromDb(fragment: Fragment){
         fireStore.collection(Constants.PRODUCTS)
             .whereEqualTo(Constants.USER_ID,getCurrentUserId())
             .get()
             .addOnSuccessListener {
                 document->
                     val productList:ArrayList<Products> =ArrayList()
                       for (i in document.documents){
                           val product= i.toObject(Products::class.java)
                           product!!.productId= i.id
                           productList.add(product)
                       }

                         when(fragment){
                             is ProductFragment->{
                                 fragment.gettingProductSuccessfully(productList)
                             }

                         }

             }
             .addOnFailureListener {
                 e->
                 Log.e("Product Retreival","failed to get product",e)
             }


     }
    fun getDashBoardItemList(fragment: DashboardFragment){
        fireStore.collection(Constants.PRODUCTS)
            .get()
            .addOnSuccessListener {
                document->

                val productList:ArrayList<Products> = ArrayList()
                 for (i in document.documents){
                     val product=i.toObject(Products::class.java)!!
                     product.productId=i.id
                     productList.add(product)
                 }
               fragment.gettingDashBoardProductSuccessfully(productList)


            }
            .addOnFailureListener {
                e->
                Log.e("DashBoard Products Not Received","Error loading DashBoard products")
            }

    }

    fun deleteProduct(fragment: ProductFragment,productId:String){
        fireStore.collection(Constants.PRODUCTS)
            .document(productId)
            .delete()
            .addOnSuccessListener {
                fragment.deletionOfProductSuccessfull()
            }
            .addOnFailureListener {
                e->
                Log.e("Deletion Failed","Unable to Delete Product",e)
            }


    }
    fun getProductDetails(activity: ProductDetailActivity,productId: String){
        fireStore.collection(Constants.PRODUCTS)
            .document(productId)
            .get()
            .addOnSuccessListener {
                document->
                val product=document.toObject(Products::class.java)!!

                activity.productReceivedSuccessfully(product)

            }



    }



}