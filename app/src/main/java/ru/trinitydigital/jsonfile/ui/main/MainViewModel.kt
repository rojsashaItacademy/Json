package ru.trinitydigital.jsonfile.ui.main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.trinitydigital.jsonfile.utils.FileUtils
import java.lang.StringBuilder

class MainViewModel : ViewModel() {

    val pdf = MutableLiveData<Uri>()

    fun loadData() {
        val data = FileUtils.getWordData()
    }

    fun replaceWords(message: String): String {

        val result = message.split(" ")
        if (result.size == 1) {
            return message
        }

        val builder = StringBuilder()

        for (i in result.size - 1 downTo 0 step 1) {
            builder.append(result[i])
            if (i != 0) builder.append(" ")
        }

        return builder.toString()
    }
}