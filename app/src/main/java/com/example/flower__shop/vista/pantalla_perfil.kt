package com.example.flower__shop.vista

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flower__shop.R
import com.example.flower__shop.presentador.clsPerfilPresentador
import com.google.android.material.button.MaterialButton

class pantalla_perfil : AppCompatActivity() {

    private lateinit var presentador: clsPerfilPresentador

    private lateinit var txtNombreUsuario: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var txtTelefono: TextView
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
        txtTelefono = findViewById(R.id.txtTelefono)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        navInicio = findViewById(R.id.navInicio)
        navExplorar = findViewById(R.id.navExplorar)
        navCarrito = findViewById(R.id.navCarrito)
        navPerfil = findViewById(R.id.navPerfil)

        val prefs = getSharedPreferences("sesion_cliente", MODE_PRIVATE)
        val idCliente = intent.getIntExtra("id_cliente", prefs.getInt("id_cliente", 1))

        presentador = clsPerfilPresentador(this, idCliente)
        presentador.iniciar()

        configurarEventos()
    }

    private fun configurarEventos() {
        btnCerrarSesion.setOnClickListener {
            val prefs = getSharedPreferences("sesion_cliente", MODE_PRIVATE)
            prefs.edit().clear().apply()
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            finish()
        }

        navInicio.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        navExplorar.setOnClickListener {
            startActivity(Intent(this, pantalla_explorar::class.java))
        }

        navCarrito.setOnClickListener {
            startActivity(Intent(this, pantalla_carrito::class.java))
        }
    }

    fun mostrarPerfil(nombreCompleto: String, correo: String, telefono: String) {
        txtNombreUsuario.text = nombreCompleto
        txtCorreo.text = correo
        txtTelefono.text = telefono
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}