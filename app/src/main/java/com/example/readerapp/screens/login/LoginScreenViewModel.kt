package com.example.readerapp.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassWord(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(
                            "Firebase",
                            "signInWithEmailAndPassWord: Success!! ${task.result.toString()}"
                        )
                        //TODO: take them home
                        home()
                    } else {
                        Log.d("Firebase", "signInWithEmailAndPassWord: ${task.result.toString()}")
                    }
                }
            } catch (ex: Exception) {
                Log.d("Firebase", "signInWithEmailAndPassWord: ${ex.message}")
            }
        }


    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //me@gmail.com
                    val displayName = task.result?.user?.email?.split("@")?.get(0)
                    createUser(displayName)
                    home()
                } else {
                    Log.d("Firebase", "createUserWithEmailAndPassword: ${task.result.toString()}")
                }
                _loading.value = false
            }
        }

    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
//        val user = mutableMapOf<String, Any>()
        val user = MUser(
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Life is greate",
            profession = "Android Developer",
            id = null
        ).toMap()

        FirebaseFirestore.getInstance().collection("users").add(user)
    }


}