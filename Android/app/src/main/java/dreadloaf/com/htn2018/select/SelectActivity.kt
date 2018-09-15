package dreadloaf.com.htn2018.select

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import dreadloaf.com.htn2018.Mole
import dreadloaf.com.htn2018.R
import dreadloaf.com.htn2018.interact.InteractActivity

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

        populateRecylcerView(listOf<Mole>())

        findViewById<FloatingActionButton>(R.id.add_mole_button).setOnClickListener({
            val intent = Intent(this, InteractActivity::class.java)
            startActivity(intent)
        })

    }

    override fun populateRecylcerView(moles: List<Mole>) {
        mRecyclerView.adapter = MyAdapter(moles)
    }


}