package com.example.assignment4.model

data class Tags(var tagIds: ArrayList<Int>) {
    override fun toString(): String {
        var string = ""
        for (tag in tagIds){
            string += (tag)
        }
        return super.toString()
    }
}