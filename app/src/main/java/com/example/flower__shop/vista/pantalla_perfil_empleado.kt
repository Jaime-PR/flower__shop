package com.example.flower__shop.vista

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flower__shop.R
import com.example.flower__shop.presentador.clsPerfilEmpleadoPresentador
import com.google.android.material.button.MaterialButton

class pantalla_perfil_empleado : AppCompatActivity() {

    private lateinit var presentador: clsPerfilEmpleadoPresentador

    private lateinit var txtNombreUsuario: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var txtTelefono: TextView
    private lateinit var btnCerrarSesion: MaterialButton

    private lateinit var navPedidos: LinearLayout
    private lateinit var navPerfil: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_perfil_empleado)

        txtNombreUsuario = findViewById(R.id.txtNombreUsuario)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtTelefono = findViewById(R.id.txtTelefono)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        navPedidos = findViewById(R.id.navPedidos)
        navPerfil = findViewById(R.id.navPerfil)

        val prefs = getSharedPreferences("sesion_usuario", MODE_PRIVATE)
        val idEmpleado = intent.getIntExtra("id_empleado", prefs.getInt("id_empleado", 0))
        val correoEmpleado = intent.getStringExtra("correo") ?: prefs.getString("correo", "") ?: ""

        presentador = clsPerfilEmpleadoPresentador(this, idEmpleado, correoEmpleado)
        presentador.iniciar()

        configurarEventos()
    }

    private fun configurarEventos() {
        btnCerrarSesion.setOnClickListener {
            val prefs = getSharedPreferences("sesion_usuario", MODE_PRIVATE)
            prefs.edit().clear().apply()
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            finish()
        }

        navPedidos.setOnClickListener {
            //pantalla envio
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