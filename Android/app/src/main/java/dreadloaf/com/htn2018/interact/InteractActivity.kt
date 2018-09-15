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
import android.widget.TextView
import dreadloaf.com.htn2018.DateUtils
import dreadloaf.com.htn2018.Mole


class InteractActivity  : AppCompatActivity(), InteractView {

    lateinit var mPresenter : InteractPresenter
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var photoURI : Uri

    lateinit var mImagePath :String

    lateinit var mImageView : ImageView
    lateinit var mDateText : TextView

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interact)

        mImageView = findViewById(R.id.mole_image)
        mDateText = findViewById(R.id.date_logged_interact_view)



        mPresenter = InteractPresenter(this, InteractInteractor())

        mDateText.text = DateUtils.getTodayDateFormatted()


        findViewById<Button>(R.id.take_photo_button).setOnClickListener({
            val takePictureIntent : Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(takePictureIntent.resolveActivity(packageManager) != null) {

                val photoFile = mPresenter.createFile(this)
                mImagePath = photoFile.absolutePath
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

    override fun onSuccessfulAnalysis(probability: Double, type: String) {
        var risk = ""
        if(type == "Malignant"){
            risk = "High"
        }else{
            risk = "Low"
        }
        val mole = Mole(-1, mDateText.text.toString(), probability, risk, listOf(probability), listOf(mDateText.text.toString()), mImagePath)
        Log.e("InteractActivity", "saving moles")
        mPresenter.saveMoles(mole)
    }

    override fun onSuccessfulSave() {
        Toast.makeText(this, "Successfully Saved Moles", Toast.LENGTH_LONG).show()
        finish()
    }
}