package dreadloaf.com.htn2018

enum class Risk{Low, Medium, High}

class Mole(val num : Int, var date : String, val riskPercent : Float, val riskHistory : Array<Float>, val dateHistory : Array<String>){
    fun onUpdate(){

    }
}
