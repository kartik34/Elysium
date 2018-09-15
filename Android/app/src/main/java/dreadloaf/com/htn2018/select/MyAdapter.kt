package dreadloaf.com.htn2018.select

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import dreadloaf.com.htn2018.Mole
import android.view.LayoutInflater
import android.widget.TextView
import dreadloaf.com.htn2018.R
import android.support.annotation.NonNull
import android.widget.AdapterView
import dreadloaf.com.htn2018.Risk


class MyAdapter(private val moles : List<Mole>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
       return moles.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bind(moles[p1].num.toString(), moles[p1].date, moles[p1].riskPercent)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.mole_list_item, p0, false)
        return MyViewHolder(view)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mNumberText : TextView
        var mDateText : TextView
        var mRiskText : TextView

        init {
            mNumberText = itemView.findViewById(R.id.mole_number)
            mDateText = itemView.findViewById(R.id.date_logged)
            mRiskText = itemView.findViewById(R.id.risk_level)
        }

        fun bind(num: String, date : String, risk : Float){
            mNumberText.text = num
            mDateText.text = date
            mRiskText.text = risk.toString()
        }



    }

}