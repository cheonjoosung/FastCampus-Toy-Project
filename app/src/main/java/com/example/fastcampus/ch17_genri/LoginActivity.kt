package com.example.fastcampus.ch17_genri

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.databinding.ActivityLogin2Binding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogin2Binding

    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) { //로그인 실패

        } else if (token != null) { //로그인 성공
            Log.e(localClassName, "callback token == $token")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kakaoTalkLoginButton.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                //카카오톡 로그인
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)

                    } else if (token != null) {
                        Log.e(localClassName, "token == $token")
                    }
                }
            } else {
                //카카오톡 계정 로그인
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }
}