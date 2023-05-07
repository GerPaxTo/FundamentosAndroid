package com.example.fundamentos

import android.arch.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AplicationActivityViewModel() : ViewModel() {
    private val _numHero = MutableLiveData<Int>(0)
    val quantity: MutableLiveData<Int> = _numHero

    private val _name = MutableLiveData<String>("")
    val name: MutableLiveData<String> = _name

    private val _vida = MutableLiveData<Int>(0)
    val vida: MutableLiveData<Int> = _vida

    private val _dano = MutableLiveData<Int>(0)
    val dano: MutableLiveData<Int> = _dano

    private val _photo = MutableLiveData<String>("")
    val photo: MutableLiveData<String> = _photo


    fun setnumHero(numHero: Int) {
        _numHero.value = numHero
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setPhoto(photo: String) {
        _photo.value = photo
    }

    fun setVida(vida: Int) {
        _vida.value = vida
    }

    fun setDano(dano: Int) {
        _dano.value = dano
    }
}