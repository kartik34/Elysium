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
import android.R.attr.data
import android.support.v4.app.NotificationCompat.getExtras
import android.util.Log
import android.widget.ImageView


class InteractActivity  : AppCompatActivity(), InteractView {

    lateinit var mPresenter : InteractPresenter
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var photoURI : Uri

    lateinit var mImageView : ImageView

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interact)

        mImageView = findViewById(R.id.mole_image)

        mPresenter = InteractPresenter(this, InteractInteractor())
        findViewById<Button>(R.id.take_photo_button).setOnClickListener({
            val takePictureIntent : Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(takePictureIntent.resolveActivity(packageManager) != null) {

                val photoFile = mPresenter.createFile(this)
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile)

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                Log.e("InteractActivity", "started activity")
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)
        Log.e("InteractActivity", "on activity result")
        if(requestCode == 1 && resultCode == RESULT_OK){
            val extras = data?.extras

            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, photoURI)
            mImageView.setImageBitmap(bitmap)

            mPresenter.uploadImageToStorage(photoURI)
        }
    }

    override fun onSuccessfulUpload() {
        Toast.makeText(this, "Successfuly uploaded file", Toast.LENGTH_LONG).show()
        mPresenter.analyze(photoURI, this)

    }
    override fun onFailedUpload() {
        Toast.makeText(this, "Failed to upload file", Toast.LENGTH_LONG).show()
    }
}