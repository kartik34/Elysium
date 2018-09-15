package dreadloaf.com.htn2018

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Client {
    @Headers("Prediction-Key: a2c7e1f6ea934679b0e571703ab7575c")
    @POST("{projectId}/image")
    fun analyzeImage(@Body bytes: RequestBody,  @Path("projectId") projectId: String) : Call<ResponseBody>
}