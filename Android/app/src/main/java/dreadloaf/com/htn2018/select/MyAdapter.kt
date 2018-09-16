package dreadloaf.com.htn2018.select

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import dreadloaf.com.htn2018.Mole
import android.view.LayoutInflater
import android.widget.TextView
import dreadloaf.com.htn2018.R
import android.support.annotation.NonNull
import android.util.Log
import android.widget.AdapterView
import android.widget.ImageView
import de.hdodenhof.circleimageview.CircleImageView
import dreadloaf.com.htn2018.Risk
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.roundToInt


class MyAdapter(private val moles : List<Mole>, val listener : CardViewOnClickListener) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    interface CardViewOnClickListener{
        fun onClick(id: Long, date: String, riskPercent : Double, riskValue:String, imageDir: String)
    }

    init {
        Log.e("MyAdater", "size of list: " + moles.size)
    }

    override fun getItemCount(): Int {
       return moles.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bind(moles[p1].num.toString(), moles[p1].date, moles[p1].riskPercent, moles[p1].imageDir, moles[p1].riskValue ,listener)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.mole_list_item, p0, false)
        return MyViewHolder(view)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var mNumberText : TextView
        private var mDateText : TextView
        private var mRiskText : TextView
        var mPreviewImage : CircleImageView
        lateinit var mImageDir : String
        lateinit var mRiskValueText : TextView
        lateinit var  mRiskValue :String
        lateinit var mListener: CardViewOnClickListener

        init {

            mNumberText = itemView.findViewById(R.id.mole_number)
            mRiskValueText = itemView.findViewById(R.id.risk_text_cardview)
            mDateText = itemView.findViewById(R.id.date_logged)
            mRiskText = itemView.findViewById(R.id.risk_level)
            mPreviewImage = itemView.findViewById(R.id.mole_preview_image)

            mNumberText.visibility = View.GONE
            itemView.setOnClickListener(this)
        }

        fun bind(num: String, date : String, risk : Double, imageDir : String, riskValue:String, listener: CardViewOnClickListener){
            mNumberText.text = num
            mDateText.text = date

            var formattedRisk = (risk * 100).roundToInt()
            if(riskValue == "Low"){
                formattedRisk = 100 - formattedRisk
            }

            mRiskText.text = formattedRisk.toString() + "%"
            mImageDir = imageDir
            mListener = listener
            mRiskValue = riskValue
            mRiskValueText.text = mRiskValue + " Risk: "
            val bitmap = BitmapFactory.decodeFile(imageDir)
            mPreviewImage.setImageBitmap(bitmap)
        }

        override fun onClick(p0: View?) {
            mListener.onClick(mNumberText.text.toString().toLong(),
                    mDateText.text.toString(),
                    mRiskText.text.toString().substring(0, mRiskText.text.toString().length-1).toDouble(),
                    mRiskValue,
                    mImageDir)
        }



    }

}