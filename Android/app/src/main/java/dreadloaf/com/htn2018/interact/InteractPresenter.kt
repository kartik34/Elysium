package dreadloaf.com.htn2018.interact

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import dreadloaf.com.htn2018.Mole
import java.io.File

class InteractPresenter(private var mView: InteractView?, private val mInteractor: InteractInteractor) : InteractInteractor.OnUploadCompleteListener, InteractInteractor.OnAnalysisCompleteListener {
    override fun onSuccessfulSave() {
        mView?.onSuccessfulSave()
    }

    override fun onSuccessfulAnalysis(probability: Double, type: String) {
        mView?.onSuccessfulAnalysis(probability, type)
    }

    override fun onFailure() {
        mView?.onFailedUpload()
    }

    override fun onSuccess() {
        mView?.onSuccessfulUpload()
    }

    fun saveMoles(newMole : Mole){
        mInteractor.initSaveMoles(newMole, this)
    }

    fun encodeImageAndUpload(bitMap: Bitmap){
        //mInteractor.encodeImageAndUpload(bitMap)
    }

    fun createFile(context: Context) =  mInteractor.createImageFile(context)


    fun uploadImageToStorage(imageUri: Uri){
        mInteractor.uploadImageToStorage(imageUri, this)
    }

    fun analyze(photoUri : Uri, context: Context){
        mInteractor.analyzeImage(photoUri, context, this)
    }

}