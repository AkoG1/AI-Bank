package com.example.aibank.ui.utils

import android.content.Context
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

fun uploadUserData(uid: String, username: String, phoneNumber: String, context: Context, money: Double) {
    val databaseReference = FirebaseDatabase.getInstance().getReference("Users")
    val user = User(uid = uid, username = username, phoneNumber = phoneNumber)
    if (uid!= "") {
        databaseReference.child(uid).setValue(user).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "User data was uploaded successfully", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(context, "Failed to upload user data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}