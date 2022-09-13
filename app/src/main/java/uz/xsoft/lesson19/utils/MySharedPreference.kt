package uz.xsoft.lesson19.utils

import android.content.Context
import android.content.SharedPreferences


class MySharedPreference private constructor(context: Context) {


    companion object {
        var mySharedPreference: MySharedPreference? = null
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        fun getInstance(context: Context): MySharedPreference? {
            if (mySharedPreference == null) {
                mySharedPreference = MySharedPreference(context)
            }
            return mySharedPreference
        }
    }

    init {
        sharedPreferences = context.getSharedPreferences("one_two", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    var elements: String
        get() = sharedPreferences.getString("elements", "").toString()
        set(elements) {
            editor.putString("elements", elements).apply()
        }

    var score: Int
        get() = sharedPreferences.getInt("score", 0)
        set(score) {
            editor.putInt("score", score).apply()
        }

}
