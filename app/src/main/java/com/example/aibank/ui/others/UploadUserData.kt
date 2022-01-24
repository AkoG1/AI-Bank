package com.example.aibankv10.ui.others

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.aibank.ui.others.User
import com.google.firebase.database.FirebaseDatabase

fun uploadUserData(uid: String, username: String, phoneNumber: String, context: Context) {
    Log.d("1111", "uploadUserData: ")
    val databaseReference = FirebaseDatabase.getInstance().getReference("Users")
    Log.d("1111", "uploadUserData: ")
    val user = User(uid = uid, username = username, phoneNumber = phoneNumber)
    Log.d("1111", "uploadUserData: ")
    if (uid!= "") {
        Log.d("1111", "uploadUserData: ")
        databaseReference.child(uid).setValue(user).addOnCompleteListener {
            Log.d("1111", "uploadUserData: ")
            if (it.isSuccessful) {
                Log.d("1111", "uploadUserData: ")
                Toast.makeText(context, "User data was uploaded successfully", Toast.LENGTH_SHORT).show()
            }else {
                Log.d("1111", "uploadUserData: F")
                Toast.makeText(context, "Failed to upload user data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}