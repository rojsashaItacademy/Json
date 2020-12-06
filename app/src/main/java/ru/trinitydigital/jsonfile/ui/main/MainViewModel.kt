package ru.trinitydigital.jsonfile.ui.main

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.trinitydigital.jsonfile.R
import ru.trinitydigital.jsonfile.utils.FileUtils
import java.lang.StringBuilder

class MainViewModel(private val context: Context) : ViewModel() {

    val pdf = MutableLiveData<Uri>()

    fun loadData() {
        val data = FileUtils.getWordData()
    }

    fun loadPdf(): String {
        return FileUtils.loadPfdFromAsset()[0]
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


    fun loadWord(): String = context.getString(R.string.app_name1)
}