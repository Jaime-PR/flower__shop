package com.example.flower__shop.vista

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flower__shop.R
import com.example.flower__shop.presentador.clsPerfilPresentador
import com.google.android.material.button.MaterialButton

class pantalla_perfil : AppCompatActivity() {

    private lateinit var presentador: clsPerfilPresentador

    private lateinit var txtNombreUsuario: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var btnCerrarSesion: MaterialButton

    private lateinit var navInicio: LinearLayout
    private lateinit var navExplorar: LinearLayout
    private lateinit var navCarrito: LinearLayout
    private lateinit var navPerfil: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_perfil)

        txtNombreUsuario = findViewById(R.id.txtNombreUsuario)
        txtCorreo = findViewById(R.id.txtCorreo)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        navInicio = findViewById(R.id.navInicio)
        navExplorar = findViewById(R.id.navExplorar)
        navCarrito = findViewById(R.id.navCarrito)
        navPerfil = findViewById(R.id.navPerfil)

        presentador = clsPerfilPresentador(this)
        configurarEventos()

        presentador.cargarPerfil(
            intent.getStringExtra("nombre"),
            intent.getStringExtra("correo")
        )
    }

    fun mostrarDatosPerfil(nombre: String, correo: String) {
        txtNombreUsuario.text = nombre
        txtCorreo.text = correo
    }

    private fun configurarEventos() {
        btnCerrarSesion.setOnClickListener {
            presentador.cerrarSesion()
        }

        navInicio.setOnClickListener {
            // Navegar a Inicio cuando se conecte esa Activity
        }

        navExplorar.setOnClickListener {
            startActivity(Intent(this, pantalla_explorar::class.java))
            finish()
        }

        navCarrito.setOnClickListener {
            startActivity(Intent(this, pantalla_carrito::class.java))
            finish()
        }
    }

    fun navegarALogin() {
        val intent = Intent(this, iniciar_sesion_cliente::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}