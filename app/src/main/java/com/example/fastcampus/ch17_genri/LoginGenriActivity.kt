package com.example.fastcampus.ch17_genri

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.databinding.ActivityLogin2Binding
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User

class LoginGenriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogin2Binding

    private lateinit var emailLoginResult: ActivityResultLauncher<Intent>

    private lateinit var pendingUser: User

    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) { //로그인 실패
            Toast.makeText(this, "로그인에 실패했습니다", Toast.LENGTH_SHORT).show()
        } else if (token != null) { //로그인 성공
            getKakaoAccountInfo()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error == null) {
                    getKakaoAccountInfo()
                }
            }
        }

        binding.kakaoTalkLoginButton.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                //카카오톡 로그인
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            Toast.makeText(this, "로그인에 실패했습니다", Toast.LENGTH_SHORT).show()
                            return@loginWithKakaoTalk
                        }
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)

                    } else if (token != null) {
                        if (Firebase.auth.currentUser == null) {
                            // 카카오톡에서 정보를 가져와 파이어베이스 로그인
                            getKakaoAccountInfo()
                        } else {
                            //
                            navigateToMapActivity()
                        }
                    }
                }
            } else {
                //카카오톡 계정 로그인
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        emailLoginResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val email = it.data?.getStringExtra("email")
                    if (email.isNullOrEmpty()) {
                        Toast.makeText(this, "이메일이 없습니다.", Toast.LENGTH_SHORT).show()
                        return@registerForActivityResult
                    } else {
                        signInFirebase(pendingUser, email)
                    }
                }
            }
    }

    private fun getKakaoAccountInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                error.printStackTrace()
                Toast.makeText(this, "로그인에 실패했습니다", Toast.LENGTH_SHORT).show()
                return@me
            } else if (user != null) {
                Log.e(
                    localClassName,
                    "회원번호: ${user.id} 이메일: ${user.kakaoAccount?.email}  닉네임: ${user.kakaoAccount?.profile?.nickname}  이미지: ${user.kakaoAccount?.profile?.profileImageUrl}"
                )

                checkKakaoUserData(user)
            }
        }
    }

    private fun checkKakaoUserData(user: User) {
        val email = user.kakaoAccount?.email

        if (email.isNullOrEmpty()) {
            pendingUser = user
            emailLoginResult.launch(Intent(this, InputEmailActivity::class.java))
            return
        }

        signInFirebase(user, email)
    }

    private fun signInFirebase(user: User, email: String) {
        val uId = user.id.toString()

        Firebase.auth.createUserWithEmailAndPassword(email, uId).addOnCompleteListener {
            if (it.isSuccessful) {
                updateFirebaseDatabase(user)
            }
        }.addOnFailureListener { error1 ->
            if (error1 is FirebaseAuthUserCollisionException) {
                Firebase.auth.signInWithEmailAndPassword(email, uId).addOnCompleteListener {
                    if (it.isSuccessful) {
                        updateFirebaseDatabase(user)
                    }
                }.addOnFailureListener { error2 ->
                    Toast.makeText(this, "이미 가입된 계정입니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "이미 가입된 계정입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateFirebaseDatabase(user: User) {
        val uid = Firebase.auth.currentUser?.uid.orEmpty()

        val personMap = mutableMapOf<String, Any>()
        personMap["uid"] = uid
        personMap["name"] = user.kakaoAccount?.profile?.nickname.orEmpty()
        personMap["profilePhoto"] = user.kakaoAccount?.profile?.thumbnailImageUrl.orEmpty()

        Firebase.database.reference.child("Person").child(uid).updateChildren(personMap)

    }

    private fun navigateToMapActivity() {
        startActivity(Intent(this, GenriActivity::class.java))
    }
}