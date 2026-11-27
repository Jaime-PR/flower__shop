package com.example.flower__shop.vista

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.flower__shop.R
import com.example.flower__shop.modelo.dataProductoCarrito
import com.example.flower__shop.modelo.dataResumenCarritoResponse
import com.example.flower__shop.presentador.clsEnvioPresentador
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class pantalla_envio : AppCompatActivity() {

    private lateinit var presentador: clsEnvioPresentador

    private lateinit var etNombreDestinataria: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etColonia: EditText
    private lateinit var etCiudad: EditText
    private lateinit var etCodigoPostal: EditText
    private lateinit var etFechaEntrega: EditText
    private lateinit var etFranjaHoraria: EditText
    private lateinit var etNotaPersonalizada: EditText

    private lateinit var tvTituloResumen: TextView
    private lateinit var contenedorProductosResumen: LinearLayout
    private lateinit var tvSubtotal: TextView
    private lateinit var tvEnvio: TextView
    private lateinit var tvTotal: TextView

    private lateinit var btnVolver: MaterialButton
    private lateinit var btnContinuarPago: MaterialButton

    private var idClienteActual: Int = 0
    private val calendarioSeleccionado: Calendar = Calendar.getInstance()

    private var horaSeleccionada: Int = -1
    private var minutoSeleccionado: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_envio)

        idClienteActual = obtenerIdCliente()
        presentador = clsEnvioPresentador(this)

        enlazarVistas()
        configurarSelectores()
        configurarEventos()

        presentador.cargarResumenPedido(idClienteActual)
    }

    override fun onResume() {
        super.onResume()
        idClienteActual = obtenerIdCliente()
        if (::presentador.isInitialized) {
            presentador.cargarResumenPedido(idClienteActual)
        }
    }

    private fun obtenerIdCliente(): Int {
        val prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE)
        val sesionActiva = prefs.getBoolean("sesion_activa", false)
        val tipoUsuario = prefs.getString("tipo_usuario", "")

        if (sesionActiva && tipoUsuario == "cliente") {
            val idPrefs = prefs.getInt("id_cliente", 0)
            if (idPrefs > 0) return idPrefs
        }

        val idIntent = intent.getIntExtra("id_cliente", 0)
        if (idIntent > 0) return idIntent

        return 0
    }

    private fun enlazarVistas() {
        etNombreDestinataria = findViewById(R.id.etNombreDestinataria)
        etTelefono = findViewById(R.id.etTelefono)
        etDireccion = findViewById(R.id.etDireccion)
        etColonia = findViewById(R.id.etColonia)
        etCiudad = findViewById(R.id.etCiudad)
        etCodigoPostal = findViewById(R.id.etCodigoPostal)
        etFechaEntrega = findViewById(R.id.etFechaEntrega)
        etFranjaHoraria = findViewById(R.id.etFranjaHoraria)
        etNotaPersonalizada = findViewById(R.id.etNotaPersonalizada)

        tvTituloResumen = findViewById(R.id.tvTituloResumen)
        contenedorProductosResumen = findViewById(R.id.contenedorProductosResumen)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvEnvio = findViewById(R.id.tvEnvio)
        tvTotal = findViewById(R.id.tvTotal)

        btnVolver = findViewById(R.id.btnVolver)
        btnContinuarPago = findViewById(R.id.btnContinuarPago)
    }

    private fun configurarSelectores() {
        val clicFecha = View.OnClickListener {
            val hoy = Calendar.getInstance()
            val anio = calendarioSeleccionado.get(Calendar.YEAR)
            val mes = calendarioSeleccionado.get(Calendar.MONTH)
            val dia = calendarioSeleccionado.get(Calendar.DAY_OF_MONTH)

            val dialogoFecha = DatePickerDialog(
                this,
                { _, anioElegido, mesElegido, diaElegido ->
                    calendarioSeleccionado.set(Calendar.YEAR, anioElegido)
                    calendarioSeleccionado.set(Calendar.MONTH, mesElegido)
                    calendarioSeleccionado.set(Calendar.DAY_OF_MONTH, diaElegido)

                    val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    etFechaEntrega.setText(formatoFecha.format(calendarioSeleccionado.time))

                    if (horaSeleccionada != -1 && minutoSeleccionado != -1) {
                        if (esFechaHoy(calendarioSeleccionado) && esHoraPasada(horaSeleccionada, minutoSeleccionado)) {
                            etFranjaHoraria.setText("")
                            horaSeleccionada = -1
                            minutoSeleccionado = -1
                            Toast.makeText(
                                this,
                                "La hora previamente seleccionada ya expiró para la fecha de hoy.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                },
                anio,
                mes,
                dia
            )

            dialogoFecha.datePicker.minDate = hoy.timeInMillis - 1000
            dialogoFecha.show()
        }

        etFechaEntrega.setOnClickListener(clicFecha)

        val clicHora = View.OnClickListener {
            if (etFechaEntrega.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Primero selecciona una fecha de entrega.", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            val ahora = Calendar.getInstance()
            val horaInicial = if (horaSeleccionada != -1) horaSeleccionada else ahora.get(Calendar.HOUR_OF_DAY)
            val minutoInicial = if (minutoSeleccionado != -1) minutoSeleccionado else ahora.get(Calendar.MINUTE)

            val dialogoHora = TimePickerDialog(
                this,
                { _, horaElegida, minutoElegido ->
                    if (esFechaHoy(calendarioSeleccionado) && esHoraPasada(horaElegida, minutoElegido)) {
                        Toast.makeText(
                            this,
                            "La hora seleccionada no puede ser anterior a la hora actual.",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        horaSeleccionada = horaElegida
                        minutoSeleccionado = minutoElegido
                        val horaFormateada = String.format(Locale.getDefault(), "%02d:%02d", horaElegida, minutoElegido)
                        etFranjaHoraria.setText(horaFormateada)
                    }
                },
                horaInicial,
                minutoInicial,
                true
            )
            dialogoHora.show()
        }

        etFranjaHoraria.setOnClickListener(clicHora)
    }

    private fun esFechaHoy(calendario: Calendar): Boolean {
        val hoy = Calendar.getInstance()
        return calendario.get(Calendar.YEAR) == hoy.get(Calendar.YEAR) &&
                calendario.get(Calendar.DAY_OF_YEAR) == hoy.get(Calendar.DAY_OF_YEAR)
    }

    private fun esHoraPasada(hora: Int, minuto: Int): Boolean {
        val ahora = Calendar.getInstance()
        val horaActual = ahora.get(Calendar.HOUR_OF_DAY)
        val minutoActual = ahora.get(Calendar.MINUTE)

        if (hora < horaActual) return true
        if (hora == horaActual && minuto < minutoActual) return true
        return false
    }

    private fun configurarEventos() {
        btnVolver.setOnClickListener {
            finish()
        }

        btnContinuarPago.setOnClickListener {
            val nombre = etNombreDestinataria.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val direccion = etDireccion.text.toString().trim()
            val colonia = etColonia.text.toString().trim()
            val ciudad = etCiudad.text.toString().trim()
            val codigoPostal = etCodigoPostal.text.toString().trim()
            val fechaEntrega = etFechaEntrega.text.toString().trim()
            val franjaHoraria = etFranjaHoraria.text.toString().trim()
            val notaPersonalizada = etNotaPersonalizada.text.toString().trim()

            presentador.validarYRegistrarEnvio(
                idCliente = idClienteActual,
                nombre = nombre,
                telefono = telefono,
                direccion = direccion,
                colonia = colonia,
                ciudad = ciudad,
                codigoPostal = codigoPostal,
                fechaEntrega = fechaEntrega,
                franjaHoraria = franjaHoraria,
                notaPersonalizada = notaPersonalizada
            )
        }
    }

    fun mostrarResumen(productos: List<dataProductoCarrito>, resumen: dataResumenCarritoResponse) {
        contenedorProductosResumen.removeAllViews()
        var totalArticulos = 0

        for (producto in productos) {
            totalArticulos += producto.cantidad
            val fila = LayoutInflater.from(this).inflate(
                android.R.layout.simple_list_item_2,
                contenedorProductosResumen,
                false
            )
            val textoPrincipal = fila.findViewById<TextView>(android.R.id.text1)
            val textoSecundario = fila.findViewById<TextView>(android.R.id.text2)

            textoPrincipal.text = "${producto.nombre} (x${producto.cantidad})"
            textoPrincipal.setTextColor(resources.getColor(android.R.color.black, theme))
            textoPrincipal.textSize = 13f

            textoSecundario.text = "$${String.format(Locale.getDefault(), "%.2f", producto.importe)}"
            textoSecundario.setTextColor(resources.getColor(android.R.color.darker_gray, theme))
            textoSecundario.textSize = 12f

            contenedorProductosResumen.addView(fila)
        }

        tvTituloResumen.text = "Resumen · $totalArticulos artículos"
        tvSubtotal.text = "$${String.format(Locale.getDefault(), "%.2f", resumen.subtotal)}"
        tvEnvio.text = when {
            resumen.subtotal <= 0.0 -> "---"
            resumen.envioGratis || resumen.envio <= 0.0 -> "Gratis"
            else -> "$${String.format(Locale.getDefault(), "%.2f", resumen.envio)}"
        }
        tvTotal.text = "$${String.format(Locale.getDefault(), "%.2f", resumen.total)}"
    }

    fun mostrarCargando(cargando: Boolean) {
        btnContinuarPago.isEnabled = !cargando
        btnContinuarPago.text = if (cargando) "GUARDANDO..." else "CONTINUAR A PAGO"
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun envioRegistradoExitosamente(mensaje: String, idEnvio: Int?) {
        AlertDialog.Builder(this)
            .setTitle("¡Compra Completada!")
            .setMessage(mensaje)
            .setPositiveButton("Aceptar") { _, _ ->
                val intentInicio = Intent(this, MainActivity::class.java)
                intentInicio.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intentInicio)
                finish()
            }
            .setCancelable(false)
            .show()
    }
}