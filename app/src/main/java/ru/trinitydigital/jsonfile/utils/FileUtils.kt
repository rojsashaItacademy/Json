package ru.trinitydigital.jsonfile.utils

import android.content.res.AssetManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import ru.trinitydigital.jsonfile.JsonFileApp
import ru.trinitydigital.jsonfile.data.MainWords
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

    fun loadPfdFromAsset(): MutableList<String> {
        val context = JsonFileApp.applicationContext()
        val pdfFiles: MutableList<String> = ArrayList()
        val assetManager: AssetManager = context.assets

        try {
            for (name in assetManager.list("")!!) {
                // include files which end with pdf only
                if (name.toLowerCase().endsWith("pdf")) {
                    pdfFiles.add(name)
                }
            }
        } catch (ioe: IOException) {
            val mesg = "Could not read files from assets folder"
            Log.e("TAG", mesg)
            Toast.makeText(
                context,
                mesg,
                Toast.LENGTH_LONG
            )
                .show()
        }

        return pdfFiles
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