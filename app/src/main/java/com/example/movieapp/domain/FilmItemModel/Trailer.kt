package com.example.movieapp.domain.FilmItemModel

import java.io.Serializable

data class Trailer(
    val Image:String="",
    val Video:String="",
    val Name:String="",
    val Time:String=""
):Serializable{
    private val listTime = listOf("h","min","sec")

    private fun convert(data:Long, index:Int=0, array:MutableList<Long> = ArrayList()):List<Long>{
        if(index == 0){
            val h = data/3600
            array.add(h)
            convert(data-h*3600,1,array)
        }else if( index == 1){
            val h = data/60
            array.add(h)
            convert(data-h*60,2,array)
        }else if(index==2 ){
            array.add(data)
        }
        return array
    }

    fun TimeString():String{
        val time = StringBuilder()
        time.append("Time : ")
        val timeList = convert(Time.trimIndent().toLong()).forEachIndexed{
            index,value->
            if(value.toInt() != 0) time.append(value).append(" ").append(listTime[index]).append(" ")
        }
        return time.toString()
    }
}
