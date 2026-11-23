package com.example.flower__shop.vista

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flower__shop.R
import com.example.flower__shop.modelo.dataLoginResponse
import com.example.flower__shop.presentador.clsLoginPresentador
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class iniciar_sesion_cliente : AppCompatActivity() {

    private lateinit var presentador: clsLoginPresentador

    private lateinit var etCorreo: TextInputEditText
    private lateinit var etContrasena: TextInputEditText
    private lateinit var btnIniciarSesion: MaterialButton
    private lateinit var btnCrearcuenta: MaterialButton
    private lateinit var btnVolver: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion_cliente)

        presentador = clsLoginPresentador(this)

        enlazarVistas()
        configurarEventos()
    }

    private fun enlazarVistas() {
        etCorreo = findViewById(R.id.etCorreo)
        etContrasena = findViewById(R.id.etContrasena)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnCrearcuenta = findViewById(R.id.btnCrearcuenta)
        btnVolver = findViewById(R.id.btnVolver)
    }

    private fun configurarEventos() {
        btnIniciarSesion.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val password = etContrasena.text.toString().trim()
            presentador.validarEIniciarSesion(correo, password)
        }

        btnCrearcuenta.setOnClickListener {
            startActivity(Intent(this, crear_cuenta_cliente::class.java))
            finish()
        }

        btnVolver.setOnClickListener {
            finish()
        }

        findViewById<LinearLayout>(R.id.navInicio).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.navExplorar).setOnClickListener {
            startActivity(Intent(this, pantalla_explorar::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.navCarrito).setOnClickListener {
            startActivity(Intent(this, pantalla_carrito::class.java))
            finish()
        }
    }

    fun mostrarCargando(cargando: Boolean) {
        btnIniciarSesion.isEnabled = !cargando
        btnIniciarSesion.text = if (cargando) "INICIANDO SESIÓN..." else "INICIAR SESIÓN Y CONTINUAR"
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun loginExitoso(datos: dataLoginResponse) {
        val mensajeBienvenida = datos.mensaje.ifEmpty { "¡Bienvenido, ${datos.nombre ?: "cliente"}!" }
        Toast.makeText(this, mensajeBienvenida, Toast.LENGTH_SHORT).show()

        val prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE)
        prefs.edit().apply {
            putBoolean("sesion_activa", true)
            putInt("id_cliente", datos.idCliente ?: 0)
            putString("nombre", datos.nombre ?: "")
            putString("correo", datos.correo ?: "")
            putString("telefono", datos.telefono ?: "")
            putString("tipo_usuario", datos.tipoUsuario ?: "cliente")
            apply()
        }

        startActivity(Intent(this, pantalla_explorar::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presentador.onDestroy()
    }
}