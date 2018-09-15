package dreadloaf.com.htn2018.interact

interface InteractView {
    fun onSuccessfulUpload()
    fun onFailedUpload()

    fun onSuccessfulAnalysis(probability: Double, type: String)

    fun onSuccessfulSave()
}