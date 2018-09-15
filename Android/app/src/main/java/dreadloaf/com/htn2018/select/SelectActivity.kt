package dreadloaf.com.htn2018.select

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import dreadloaf.com.htn2018.R

class SelectActivity : AppCompatActivity(), SelectView {

    lateinit var mRecyclerView : RecyclerView
    lateinit var mPresenter : SelectPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        mPresenter = SelectPresenter(this, SelectInteractor())

        mRecyclerView = findViewById(R.id.mole_list)
        val linearLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = linearLayoutManager

    }
}