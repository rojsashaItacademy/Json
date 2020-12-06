package ru.trinitydigital.jsonfile.ui.main

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.trinitydigital.jsonfile.R
import java.io.*


class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()

    private lateinit var tvTitle: TextView
    private lateinit var btnClickMe: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupListeners()
        setupViewModel()


    }

    private fun setupUI() {
        tvTitle = findViewById(R.id.tvTitle)
        btnClickMe = findViewById(R.id.btnClickMe)
    }

    private fun setupListeners() {
        btnClickMe.setOnClickListener {
//            tvTitle.text = "123456"
            checkPermisiion()
        }
    }

    private fun setupViewModel() {
        vm.pdf.observe(this, Observer {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(it, "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            startActivity(intent)
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == EXT_STORAGE_PERMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "asdasdasdasdsadsad", Toast.LENGTH_LONG).show()
            openScreen()
        }
    }

    private fun checkPermisiion() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                EXT_STORAGE_PERMISSION_CODE
            );
            Log.d(
                TAG,
                "After getting permission: " + Manifest.permission.WRITE_EXTERNAL_STORAGE + " " + ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            );

        } else {
            // We were granted permission already before
            Toast.makeText(this, "asdasdasdasdsadsad", Toast.LENGTH_LONG).show()
            openScreen()
        }
    }

    private fun openScreen(){
        val fileName = vm.loadPdf()
        copyFileFromAssets(fileName)
        /** PDF reader code */
        /** PDF reader code  */
        var uri: Uri? = null
        val file = File(
            tmpFolder
                    + "/"
                    + fileName
        )

        uri = FileProvider.getUriForFile(
            this@MainActivity,
            getString(R.string.file_provider_authority),
            file
        )
        Log.i(TAG, "Launching viewer " + fileName + " " + file.absolutePath)

        //Intent intent = new Intent(Intent.ACTION_VIEW, FileProvider.getUriForFile(v.getContext(), "org.eicsanjose.quranbasic.fileprovider", file));


        //Intent intent = new Intent(Intent.ACTION_VIEW, FileProvider.getUriForFile(v.getContext(), "org.eicsanjose.quranbasic.fileprovider", file));
        val intent = Intent(Intent.ACTION_VIEW)
        //intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        //intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setDataAndType(uri, "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
            Log.i(TAG, "Staring pdf viewer activity")
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, (e.message)!!)
        }
    }

    private fun copyFileFromAssets(fileName: String) {
        Log.i(TAG, "Copy file from asset:$fileName")
        val assetManager = this.assets


        // file to copy to from assets
        val cacheFile: File = File(tmpFolder + "/" + fileName)
        var `in`: InputStream? = null
        var out: OutputStream? = null
        try {
            Log.d(TAG, "Copying from assets folder to cache folder")
            if (cacheFile.exists()) {
                // already there. Do not copy
                Log.d(TAG, "Cache file exists at:" + cacheFile.absolutePath)
                return
            } else {
                Log.d(TAG, "Cache file does NOT exist at:" + cacheFile.absolutePath)
                // TODO: There should be some error catching/validation etc before proceeding
                `in` = assetManager.open(fileName)
                out = FileOutputStream(cacheFile)
                copyFile(`in`, out)
                `in`.close()
                `in` = null
                out.flush()
                out.close()
                out = null
            }
        } catch (ioe: IOException) {
            Log.e(TAG, "Error in copying file from assets $fileName")
            ioe.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun copyFile(`in`: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (`in`.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
    }

    companion object {
        private const val TAG = "MAIN ACTIVITY"
        private const val EXT_STORAGE_PERMISSION_CODE = 101
        private val tmpFolder = Environment.getExternalStorageDirectory().path + "/pdfFiles"
    }
}