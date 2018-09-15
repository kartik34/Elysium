package dreadloaf.com.htn2018.interact

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.SyncStateContract
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.FileProvider
import com.google.android.gms.tasks.OnSuccessListener
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.UploadTask
import java.net.URI
import dreadloaf.com.htn2018.MainActivity
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.content.FileProvider.getUriForFile
import android.widget.Toast


class InteractInteractor {

    interface OnUploadCompleteListener{
        fun onSuccess()
        fun onFailure()
    }

    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var mStorageRef : StorageReference
    private lateinit var mCurrentPhotoPath :String

    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }



    fun encodeImageAndUpload(bitmap: Bitmap){
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val encodedImage = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())


    }

    fun uploadImageToStorage(imageUri: Uri, listener: OnUploadCompleteListener){
        mStorageRef = FirebaseStorage.getInstance().reference
        val filePath = mStorageRef.child("test").child(imageUri.lastPathSegment)

        filePath.putFile(imageUri).addOnSuccessListener({ taskSnapshot ->
            val downloadUrl = taskSnapshot.downloadUrl
            listener.onSuccess()

        })
        filePath.putFile(imageUri).addOnFailureListener({taskSnapshot ->
            listener.onFailure()
        })


    }


}