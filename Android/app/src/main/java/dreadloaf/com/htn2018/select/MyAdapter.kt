package dreadloaf.com.htn2018.select

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


class MyAdapter(private val moles : List<Mole>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    init {
        Log.e("MyAdater", "size of list: " + moles.size)
    }

    override fun getItemCount(): Int {
       return moles.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bind(moles[p1].num.toString(), moles[p1].date, moles[p1].riskPercent, moles[p1].imageDir)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.mole_list_item, p0, false)
        return MyViewHolder(view)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mNumberText : TextView
        var mDateText : TextView
        var mRiskText : TextView
        var mPreviewImage : CircleImageView
        lateinit var mImageDir : String

        init {
            mNumberText = itemView.findViewById(R.id.mole_number)
            mDateText = itemView.findViewById(R.id.date_logged)
            mRiskText = itemView.findViewById(R.id.risk_level)
            mPreviewImage = itemView.findViewById(R.id.mole_preview_image)
        }

        fun bind(num: String, date : String, risk : Double, imageDir : String){
            mNumberText.text = num
            mDateText.text = date
            mRiskText.text = risk.toString()
            mImageDir = imageDir


            val file  = File(imageDir)
            val bitmap = BitmapFactory.decodeFile(imageDir)
            mPreviewImage.setImageBitmap(bitmap)
        }



    }

}