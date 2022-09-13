package uz.xsoft.lesson19

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import uz.xsoft.lesson19.contracts.GameContract
import uz.xsoft.lesson19.data.Movement
import uz.xsoft.lesson19.presenter.GamePresenterImpl
import uz.xsoft.lesson19.repository.GameRepositoryImpl
import uz.xsoft.lesson19.utils.MovementDetector
import uz.xsoft.lesson19.utils.MySharedPreference

class MainActivity : AppCompatActivity(), GameContract.View {
    private val buttons: ArrayList<TextView> = ArrayList(16)
    private lateinit var presenter: GamePresenterImpl
    private lateinit var score: TextView
    private var scoreS = 0
    private lateinit var sharedPreference: MySharedPreference
    private lateinit var matrix: Array<ArrayList<Int> /* = java.util.ArrayList<kotlin.Int> */>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = GamePresenterImpl(this, GameRepositoryImpl(this).apply {
            submitListener { list, score ->
                matrix = list
                scoreS = score
            }
        })
        loadViews()
        presenter.startGame()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun loadViews() {
        val mainContainer = findViewById<LinearLayout>(R.id.mainContainer)
        score = findViewById(R.id.score)
        for (i in 0 until mainContainer.childCount) {
            val childContainer = mainContainer.getChildAt(i) as LinearLayout
            for (j in 0 until childContainer.childCount) {
                buttons.add(childContainer.getChildAt(j) as TextView)
            }
        }

        val movementDetector = MovementDetector(this)

        movementDetector.setOnMovementListener {
            when (it) {
                Movement.LEFT -> presenter.moveLeft()
                Movement.RIGHT -> presenter.moveRight()
                Movement.DOWN -> presenter.moveDown()
                Movement.UP -> presenter.moveUp()
            }
        }

        mainContainer.setOnTouchListener(movementDetector)
    }

    override fun onPause() {
        val gson = Gson()
        super.onPause()
        sharedPreference = MySharedPreference.getInstance(this)!!
        sharedPreference.elements = gson.toJson(matrix)
        sharedPreference.score = scoreS
    }

    override fun changeState(matrix: Array<ArrayList<Int>>, score: Int) {
        this.score.text = score.toString()
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                val button = buttons[4 * i + j]
                val value = matrix[i][j]
                if (value == 0) {
                    button.text = ""
                } else {
                    button.text = matrix[i][j].toString()
                }

                button.setBackgroundResource(
                    when (value) {
                        2 -> R.drawable.number_two_back
                        4 -> R.drawable.number_four_back
                        8 -> R.drawable.eight
                        16 -> R.drawable.sixteen
                        32 -> R.drawable.back_32
                        64 -> R.drawable.back_64
                        128 -> R.drawable.back_128
                        256 -> R.drawable.back_256
                        else -> R.drawable.background_default
                    }
                )
            }
        }
    }
}