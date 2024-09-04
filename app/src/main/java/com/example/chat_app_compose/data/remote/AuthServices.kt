package com.example.chat_app_compose.data.remote

import android.net.Uri
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.FirebaseException
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

class AuthServices @Inject constructor(
    private val auth: FirebaseAuth,
){
    suspend fun getCurrentUser():FirebaseUser?{
        try {
            auth.currentUser?.reload()?.await()
        }catch (e:FirebaseException)
        {
            return null
        }
        return auth.currentUser
    }

    fun getUserEmail():String{
        return auth.currentUser?.email?:""
    }

    suspend fun signInWithGoogle(credential:Credential):String?
    {
        try {
            if(credential is CustomCredential && credential.type==TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
                val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data)
                val resultCredential = GoogleAuthProvider.getCredential(googleIdToken.idToken,null)
                auth.signInWithCredential(resultCredential).await()
            }else{
                return "Google account not valid, choose different account"
            }
        }catch (e:FirebaseException)
        {
            return e.message.toString()
        }
        return null
    }

    suspend fun signUpWithGoogle(credential: Credential):String?
    {
        try {
            if(credential is CustomCredential && credential.type==TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
                val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data)
                val resultCredential = GoogleAuthProvider.getCredential(googleIdToken.idToken,null)
                if(auth.currentUser!=null)
                {
                    auth.currentUser!!.linkWithCredential(resultCredential).await()
                }else{
                    auth.signInWithCredential(resultCredential).await()
                }
            }else{
                return "Google account not valid, choose different account"
            }
        }catch (e:FirebaseException)
        {
            return e.message.toString()
        }
        return null
    }


   suspend fun sendPasswordResetEmail(email:String):String?
    {
        try {
            auth.sendPasswordResetEmail(email).await()
        }catch (e:FirebaseException)
        {
            return e.message.toString()
        }
        return null
    }


    suspend fun isUserEmailVerified():Boolean{
        try {
            auth.currentUser?.reload()?.await()
        }catch(e:FirebaseException) {
            return false
        }
        if(auth.currentUser==null)
        {
            return false
        }
        return auth.currentUser?.isEmailVerified==true
    }

    suspend fun sendEmailVerificationMail():String?{
        var result:String?=null
        try {
            if(getCurrentUser()!=null)
            {
                auth.currentUser!!.sendEmailVerification()
                    .addOnCompleteListener {
                            task->
                        if(!task.isSuccessful)
                        {
                            result="Unknown Error , Try Again"
                        }
                    }
                    .await()
            }
        }catch (e:FirebaseException)
        {
            return e.message.toString()
        }
        return result
    }


    suspend fun createNewUser(userName:String,email:String,password:String,image: Uri?):String?
    {
        try{
            auth.createUserWithEmailAndPassword(email,password).await()
            if(auth.currentUser==null){
                return "Unknown Error , Try Again"
            }else{
                val profileUpdates = userProfileChangeRequest {
                    displayName = userName
                    photoUri = image }
                auth.currentUser!!.updateProfile(profileUpdates).await()
                Log.d("MainActivity",auth.currentUser.toString()+"---- User ,    In Auth Service")
            }
        }catch (e:FirebaseException)
        {
            return e.message.toString()
        }
        return null
    }

    suspend fun loginUser(email: String,password: String):String?
    {
        try {
            auth.signInWithEmailAndPassword(email,password).await()
            if(auth.currentUser==null)
            {
                return "Unknown Error, Try Again"
            }
        }catch (e:FirebaseException){
            return e.message.toString()
        }
        return null
    }
}