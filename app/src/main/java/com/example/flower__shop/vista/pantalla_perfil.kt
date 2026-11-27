package com.example.flower__shop.vista

import android.content.Context
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

        cargarEstadoSesion()
        configurarEventos()
    }

    override fun onResume() {
        super.onResume()
        cargarEstadoSesion()
    }

    private fun cargarEstadoSesion() {
        val prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE)
        val sesionActiva = prefs.getBoolean("sesion_activa", false)
        val idCliente = intent.getIntExtra("id_cliente", prefs.getInt("id_cliente", 0))
        val correoCliente = intent.getStringExtra("correo") ?: prefs.getString("correo", "") ?: ""

        presentador = clsPerfilPresentador(this, idCliente, correoCliente)

        val tipoUsuario = prefs.getString("tipo_usuario", "")
        if (sesionActiva && tipoUsuario == "cliente" && idCliente > 0 && correoCliente.isNotBlank()) {
            presentador.iniciar()
        } else {
            mostrarModoInvitado()
        }
    }

    private fun configurarEventos() {
        btnCerrarSesion.setOnClickListener {
            val prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE)
            val sesionActiva = prefs.getBoolean("sesion_activa", false)
            val tipoUsuario = prefs.getString("tipo_usuario", "")

            if (sesionActiva && tipoUsuario == "cliente") {
                cerrarSesion()
            } else {
                val intentLogin = Intent(this, iniciar_sesion_cliente::class.java)
                intentLogin.putExtra("volver_atras", true)
                startActivity(intentLogin)
            }
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

    private fun mostrarModoInvitado() {
        txtNombreUsuario.text = "Invitado"
        txtCorreo.text = ""
        txtTelefono.text = ""

        btnCerrarSesion.isEnabled = true
        btnCerrarSesion.text = "Iniciar Sesión"
    }

    private fun cerrarSesion() {
        getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE).edit().clear().apply()
        getSharedPreferences("sesion_cliente", Context.MODE_PRIVATE).edit().clear().apply()

        getSharedPreferences("ConfigApp", Context.MODE_PRIVATE).edit()
              .putBoolean("modo_invitado", true)
            .apply()

        intent.removeExtra("id_cliente")
        intent.removeExtra("correo")

        mostrarModoInvitado()
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
    }

    fun mostrarPerfil(nombreCompleto: String, correo: String, telefono: String) {
        txtNombreUsuario.text = nombreCompleto
        txtCorreo.text = correo
        txtTelefono.text = telefono

        btnCerrarSesion.isEnabled = true
        btnCerrarSesion.text = "Cerrar Sesión"
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}