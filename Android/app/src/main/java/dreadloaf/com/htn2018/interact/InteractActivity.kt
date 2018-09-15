package dreadloaf.com.htn2018.interact

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import dreadloaf.com.htn2018.R
import android.provider.MediaStore
import android.graphics.Bitmap
import android.net.Uri
import android.support.v4.content.FileProvider


class InteractActivity  : AppCompatActivity(), InteractView {

    lateinit var mPresenter : InteractPresenter
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var photoURI : Uri

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interact)
        mPresenter = InteractPresenter(this, InteractInteractor())
        findViewById<Button>(R.id.take_photo_button).setOnClickListener({
            val takePictureIntent : Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(takePictureIntent.resolveActivity(packageManager) != null) {

                val photoFile = mPresenter.createFile(this)
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile)

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == RESULT_OK){
            //val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, photoURI)
            mPresenter.uploadImageToStorage(photoURI)
        }
    }

    override fun onSuccessfulUpload() {
        Toast.makeText(this, "Successfuly uploaded file", Toast.LENGTH_LONG).show()
    }
    override fun onFailedUpload() {
        Toast.makeText(this, "Failed to upload file", Toast.LENGTH_LONG).show()
    }
}