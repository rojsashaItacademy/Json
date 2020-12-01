package ru.trinitydigital.jsonfile.utils

import android.content.Context
import android.content.res.AssetManager
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.google.gson.Gson
import ru.trinitydigital.jsonfile.BuildConfig
import ru.trinitydigital.jsonfile.JsonFileApp
import ru.trinitydigital.jsonfile.data.MainWords
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


const val WORDS_PATH = "words.json"

object FileUtils {

    private fun loadJsonFromAsset(path: String): String? {
        var json: String? = null
        try {
            val from = JsonFileApp.applicationContext().assets.open(path)
            val buffer = ByteArray(from.available())
            from.read(buffer)
            from.close()
            json = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return json
    }

    fun loadPfdFromAsset(): Uri {
        val assetManager: AssetManager = JsonFileApp.applicationContext().assets

        var `in`: InputStream? = null
        var out: OutputStream? = null
        val file: File = File(JsonFileApp.applicationContext().filesDir, "abc.pdf")
        val uri = FileProvider.getUriForFile(
            JsonFileApp.applicationContext(),
            BuildConfig.APPLICATION_ID.toString() + ".provider",
            file
        )
        try {
            `in` = assetManager.open("abc.pdf")
            out = JsonFileApp.applicationContext()
                .openFileOutput(file.name, Context.MODE_WORLD_READABLE)
            copyFile(`in`, out)
            `in`.close()
            `in` = null
            out!!.flush()
            out.close()
            out = null
        } catch (e: Exception) {
            Log.e("tag", e.message!!)
        }

        return uri

        Log.d("asdasdasdasd", "adasdasdsadsad")
    }

    private fun copyFile(inputStream: InputStream, outputStream: OutputStream) {
        val buffer = ByteArray(1024)

        var read = 0
        while ((read) != -1) {
            read = inputStream.read(buffer)
            outputStream.write(buffer, 0, read);
        }
    }

//    private void copyFile(InputStream in, OutputStream out) throws IOException
//    {
//        byte[] buffer = new byte[1024];
//        int read;
//        while((read = in.read(buffer)) != -1){
//            out.write(buffer, 0, read);
//        }

    fun getWordData(): MainWords {
        val json = loadJsonFromAsset(WORDS_PATH)

        val gson = Gson().fromJson(json, MainWords::class.java)
        return gson


    }
}