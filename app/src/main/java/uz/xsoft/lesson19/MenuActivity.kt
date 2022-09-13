package uz.xsoft.lesson19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val play = findViewById<AppCompatButton>(R.id.play)

        play.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}