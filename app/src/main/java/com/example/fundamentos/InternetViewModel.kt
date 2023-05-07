package com.example.fundamentos

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class InternetViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState : StateFlow<UiState> = _uiState
    var token: String = ""
    var listaHeroes = listOf<Hero>()

    fun logIn(user: String, password: String) {
        if (token.isEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val client = OkHttpClient()
                val url = "https://dragonball.keepcoding.education/api/auth/login"
                val credentials = Credentials.basic("$user", "$password")
                val body = FormBody.Builder()
                    .build()
                val request = Request.Builder()
                    .url(url)
                    .addHeader("Authorization", credentials)
                    .post(body)
                    .build()
                val call = client.newCall(request)
                val response = call.execute()
                println(response.body)
                response.body?.let { responseBody ->
                    _uiState.value = UiState.OnTokenReceived(responseBody.string())
                } ?: run { println("Error") }
            }
        }
    }

    fun downloadHeros(token: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "https://dragonball.keepcoding.education/api/heros/all"
            val body = FormBody.Builder()
                .add("name", "")
                .build()
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $token")
                .post(body)
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            response.body?.let { responseBody ->
                val gson = Gson()
                try {
                    val heroDTOtoArray = gson.fromJson(responseBody.string(), Array<HeroDTO>::class.java)
                    _uiState.value = UiState.OnHeroReceived(heroDTOtoArray.toList().map { Hero(it.id, it.name, it.photo, 100, 0) })
                } catch(ex: Exception ) {
                    _uiState.value = UiState.Error("Something went wrong in the response")
                }
            } ?: run { _uiState.value = UiState.Error("Something went wrong in the request") }
        }
    }

    sealed class UiState {
        object Idle : UiState()
        data class Error(val error: String) : UiState()
        data class OnHeroReceived(val heroes: List<Hero>) : UiState()
        data class OnTokenReceived(val text: String) : UiState()
    }
}