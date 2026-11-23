package com.example.flower__shop.vista

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flower__shop.R
import com.example.flower__shop.modelo.dataRegistroResponse
import com.example.flower__shop.presentador.clsRegistroPresentador
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class crear_cuenta_cliente : AppCompatActivity() {

    private lateinit var presentador: clsRegistroPresentador

    private lateinit var etNombre: TextInputEditText
    private lateinit var etApellidoPaterno: TextInputEditText
    private lateinit var etApellidoMaterno: TextInputEditText
    private lateinit var etCorreo: TextInputEditText
    private lateinit var etTelefono: TextInputEditText
    private lateinit var etContrasena: TextInputEditText
    private lateinit var etConfirmarContrasena: TextInputEditText
    private lateinit var btnCrearCuenta: MaterialButton
    private lateinit var btnIniciarsesion: MaterialButton
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta_cliente)

        presentador = clsRegistroPresentador(this)

        enlazarVistas()
        configurarEventos()
    }

    private fun enlazarVistas() {
        etNombre = findViewById(R.id.etNombre)
        etApellidoPaterno = findViewById(R.id.etApellidoPaterno)
        etApellidoMaterno = findViewById(R.id.etApellidoMaterno)
        etCorreo = findViewById(R.id.etCorreo)
        etTelefono = findViewById(R.id.etTelefono)
        etContrasena = findViewById(R.id.etContrasena)
        etConfirmarContrasena = findViewById(R.id.etConfirmarContrasena)
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        btnIniciarsesion = findViewById(R.id.btnIniciarsesion)
        btnVolver = findViewById(R.id.btnVolver)
    }

    private fun configurarEventos() {
        btnCrearCuenta.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apellidoPaterno = etApellidoPaterno.text.toString().trim()
            val apellidoMaterno = etApellidoMaterno.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val password = etContrasena.text.toString().trim()
            val confirmarPassword = etConfirmarContrasena.text.toString().trim()

            presentador.validarYRegistrar(
                nombre = nombre,
                apellidoPaterno = apellidoPaterno,
                apellidoMaterno = apellidoMaterno,
                correo = correo,
                telefono = telefono,
                password = password,
                confirmarPassword = confirmarPassword
            )
        }

        btnIniciarsesion.setOnClickListener {
            startActivity(Intent(this, iniciar_sesion_cliente::class.java))
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
        btnCrearCuenta.isEnabled = !cargando
        btnCrearCuenta.text = if (cargando) "CREANDO CUENTA..." else "CREAR CUENTA Y CONTINUAR"
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun registroExitoso(datos: dataRegistroResponse) {
        val mensajeBienvenida = datos.mensaje.ifEmpty { "¡Cuenta creada! Bienvenido, ${datos.nombre ?: "cliente"}!" }
        Toast.makeText(this, mensajeBienvenida, Toast.LENGTH_SHORT).show()

        val prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE)
        prefs.edit().apply {
            putBoolean("sesion_activa", true)
            putInt("id_cliente", datos.idCliente ?: 0)
            putString("nombre", datos.nombre ?: etNombre.text.toString().trim())
            putString("correo", datos.correo ?: etCorreo.text.toString().trim())
            putString("telefono", datos.telefono ?: etTelefono.text.toString().trim())
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