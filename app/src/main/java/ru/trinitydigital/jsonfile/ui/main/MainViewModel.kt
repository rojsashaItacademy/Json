package ru.trinitydigital.jsonfile.ui.main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.trinitydigital.jsonfile.utils.FileUtils

class MainViewModel : ViewModel() {

    val pdf = MutableLiveData<Uri>()

     fun loadData() {
        val data = FileUtils.getWordData()
    }
}