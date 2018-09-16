package dreadloaf.com.htn2018.select

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Button
import android.view.Menu
import dreadloaf.com.htn2018.FirebaseUtils
import dreadloaf.com.htn2018.Mole
import dreadloaf.com.htn2018.R
import dreadloaf.com.htn2018.interact.InteractActivity
import kotlinx.android.synthetic.main.activity_select.*
import android.view.MenuInflater
import android.view.MenuItem


class SelectActivity : AppCompatActivity(), SelectView, FirebaseUtils.OnMoleLoadedListener, MyAdapter.CardViewOnClickListener{

    lateinit var mRecyclerView : RecyclerView
    lateinit var mPresenter : SelectPresenter
    lateinit var mMoles : List<Mole>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        if(getSupportActionBar() !=null) getSupportActionBar()?.setDisplayShowTitleEnabled(false);
        mPresenter = SelectPresenter(this, SelectInteractor())

        //mToolBar = findViewById(R.id.toolbar)
        //setSupportActionBar(mToolBar)
        mRecyclerView = findViewById(R.id.mole_list)
        val linearLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = linearLayoutManager



        findViewById<FloatingActionButton>(R.id.add_mole_button).setOnClickListener({
            val intent = Intent(this, InteractActivity::class.java)
            startActivity(intent)
        })

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.add_mole_button_toolbar){
            val intent = Intent(this, InteractActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        mMoles = mPresenter.loadSavedMoles(this)
        Log.e("SelectAcrivity", "size of moles: "+mMoles.size.toString())
        populateRecylcerView(mMoles)
    }

    override fun populateRecylcerView(moles: List<Mole>) {
        Log.e("SelectActivity", "populating view with size " + moles.size)

        mRecyclerView.adapter = MyAdapter(moles, this)
    }

    override fun onMoleLoaded(moles: List<Mole>) {
        mMoles = moles
        Log.d("SelectActivity", moles.size.toString())
        populateRecylcerView(moles)
    }

    override fun onClick(id: Long, date: String, riskPercent: Double, riskValue: String, imageDir: String) {
        val intent = Intent(this, InteractActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("date", date)
        intent.putExtra("riskPercent", riskPercent)
        intent.putExtra("riskValue", riskValue)
        intent.putExtra("imageDir", imageDir)
        startActivity(intent)
    }

}