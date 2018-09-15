package dreadloaf.com.htn2018.interact

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File

class InteractPresenter(private var mView: InteractView?, private val mInteractor: InteractInteractor) : InteractInteractor.OnUploadCompleteListener {
    override fun onFailure() {
        mView?.onFailedUpload()
    }

    override fun onSuccess() {
        mView?.onSuccessfulUpload()
    }

    fun encodeImageAndUpload(bitMap: Bitmap){
        mInteractor.encodeImageAndUpload(bitMap)
    }

    fun createFile(context: Context) =  mInteractor.createImageFile(context)


    fun uploadImageToStorage(imageUri: Uri){
        mInteractor.uploadImageToStorage(imageUri, this)
    }

    fun onDestroy(){
        mView = null
    }

}