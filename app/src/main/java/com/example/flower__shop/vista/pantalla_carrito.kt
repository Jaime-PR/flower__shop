package com.example.flower__shop.vista

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flower__shop.R

class pantalla_carrito : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_carrito)

        val navExplorar = findViewById<LinearLayout>(R.id.navExplorar)
        navExplorar.setOnClickListener {
            val intent = Intent(this, pantalla_explorar::class.java)
            startActivity(intent)
            finish()
        }
    }
}