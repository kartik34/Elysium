package dreadloaf.com.htn2018.select

import android.content.Context

class SelectPresenter(val mView : SelectView, val mInteractor: SelectInteractor) {
    fun loadSavedMoles(context : Context){
        mInteractor.loadSavedMoles()
    }
}