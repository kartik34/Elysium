package dreadloaf.com.htn2018

enum class Risk{Low, Medium, High}

class Mole(val num : Long, var date : String, val riskPercent : Double, val riskValue :String,
           val riskHistory : List<Double>, val dateHistory : List<String>, val imageDir : String){
    fun onUpdate(){

    }
}
