package uz.xsoft.lesson19.repository

import android.content.Context
import android.util.Log
import android.view.animation.AnimationUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.xsoft.lesson19.R
import uz.xsoft.lesson19.contracts.GameContract
import uz.xsoft.lesson19.utils.MySharedPreference


class GameRepositoryImpl(private var context: Context) : GameContract.Repository {
    private var score = 0
    private var gson = Gson()
    private val listAction: Array<ArrayList<Int>> = arrayOf(
        arrayListOf(0, 0, 0, 0),
        arrayListOf(0, 0, 0, 0),
        arrayListOf(0, 0, 0, 0),
        arrayListOf(0, 0, 0, 0)
    )
    private var listener: ((Array<ArrayList<Int>>, Int) -> Unit)? = null
    private var sharedPreference: MySharedPreference

    fun submitListener(block: ((Array<ArrayList<Int>>, Int) -> Unit)) {
        this.listener = block
    }

    private var matrix = readFromData()

    init {
        sharedPreference = MySharedPreference.getInstance(context)!!
        score = sharedPreference.score

        if (sharedPreference.elements == "") {
            addElement()
            addElement()
        }
    }

    private fun addElement() {
        val elements = ArrayList<Pair<Int, Int>>()
        var value = 2
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) elements.add(Pair(i, j))
            }
        }
        elements.shuffle()
        if (elements.size == 0) return
        val element = elements[0]

        matrix[element.first][element.second] = value
        listAction[element.first][element.second] = value
    }

    override fun getMatrix(): Array<ArrayList<Int>> = matrix


    override fun moveLeft() {
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) continue;

                if (amounts.isEmpty()) {
                    amounts.add(matrix[i][j])
                } else {
                    if (amounts.last() == matrix[i][j] && state) {
                        state = false
                        amounts[amounts.lastIndex] = matrix[i][j] * 2
                        score += amounts[amounts.lastIndex]
                    } else {
                        state = true
                        amounts.add(matrix[i][j])
                    }
                }
                matrix[i][j] = 0
            }
            for (j in 0 until amounts.size) {
                matrix[i][j] = amounts[j]
            }
        }
        addElement()
        listener?.let { it(matrix, score) }
    }

    override fun moveRight() {
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0..3) {
                if (matrix[i][j] == 0) continue;

                if (amounts.isEmpty()) {
                    amounts.add(matrix[i][j])
                } else {
                    if (amounts.last() == matrix[i][j] && state) {
                        state = false
                        amounts[amounts.lastIndex] = matrix[i][j] * 2
                        score += amounts[amounts.lastIndex]
                    } else {
                        state = true
                        amounts.add(matrix[i][j])
                    }
                }
                matrix[i][j] = 0
            }


            for (j in amounts.size - 1 downTo 0) {
                matrix[i][3 - j] = amounts[j]
            }
        }
        addElement()
        listener?.let { it(matrix, score) }
    }

    override fun moveUp() {

        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0..3) {
                if (matrix[j][i] == 0) continue;

                if (amounts.isEmpty()) {
                    amounts.add(matrix[j][i])
                } else {
                    if (amounts.last() == matrix[j][i] && state) {
                        state = false
                        amounts[amounts.lastIndex] = matrix[j][i] * 2
                        score += amounts[amounts.lastIndex]
                    } else {
                        state = true
                        amounts.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }
            for (j in 0 until amounts.size) {
                matrix[j][i] = amounts[j]
            }
        }
        addElement()
        listener?.let { it(matrix, score) }
    }

    override fun getScore(): Int = score

    override fun moveDown() {
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 3 downTo 0) {
                if (matrix[j][i] == 0) continue;

                if (amounts.isEmpty()) {
                    amounts.add(matrix[j][i])
                } else {
                    if (amounts.last() == matrix[j][i] && state) {
                        state = false
                        amounts[amounts.lastIndex] = matrix[j][i] * 2
                        score += amounts[amounts.lastIndex]
                    } else {
                        state = true
                        amounts.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }

            for (j in 0 until amounts.size) {
                matrix[matrix.size - 1 - j][i] = amounts[j]
            }
        }
        addElement()
        listener?.let { it(matrix, score) }
    }

    private fun readFromData(): Array<ArrayList<Int>> {

        var list: Array<ArrayList<Int>> = arrayOf(
            arrayListOf(0, 0, 0, 0),
            arrayListOf(0, 0, 0, 0),
            arrayListOf(0, 0, 0, 0),
            arrayListOf(0, 0, 0, 0)
        )

        sharedPreference = MySharedPreference.getInstance(context)!!
        if (sharedPreference.elements.isNotEmpty()) {
            val ls: String = sharedPreference.elements
            val type = object : TypeToken<Array<ArrayList<Int>>>() {}.type
            if (ls != "") {
                list = gson.fromJson(ls, type)
            }

        } else {
            val lists: Array<ArrayList<Int>> = arrayOf(
                arrayListOf(0, 0, 0, 0),
                arrayListOf(0, 0, 0, 0),
                arrayListOf(0, 0, 0, 0),
                arrayListOf(0, 0, 0, 0)
            )
            list = lists

        }
        return list
    }
}