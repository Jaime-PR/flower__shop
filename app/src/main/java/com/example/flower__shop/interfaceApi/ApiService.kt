package com.example.flower__shop.interfaceApi

import com.example.flower__shop.modelo.dataAccionCarritoResponse
import com.example.flower__shop.modelo.dataAgregarCarritoRequest
import com.example.flower__shop.modelo.dataAgregarCarritoResponse
import com.example.flower__shop.modelo.dataCategoriaPopular
import com.example.flower__shop.modelo.dataDetalleProductoResponse
import com.example.flower__shop.modelo.dataEliminarCarritoRequest
import com.example.flower__shop.modelo.dataLoginRequest
import com.example.flower__shop.modelo.dataLoginResponse
import com.example.flower__shop.modelo.dataMotivo
import com.example.flower__shop.modelo.dataObtenerCarritoResponse
import com.example.flower__shop.modelo.dataPerfilEmpleadoRequest
import com.example.flower__shop.modelo.dataPerfilEmpleadoResponse
import com.example.flower__shop.modelo.dataPerfilRequest
import com.example.flower__shop.modelo.dataPerfilResponse
import com.example.flower__shop.modelo.dataProductoItem
import com.example.flower__shop.modelo.dataRegistroRequest
import com.example.flower__shop.modelo.dataRegistroResponse
import com.example.flower__shop.modelo.dataResumenCarritoResponse
import com.example.flower__shop.modelo.dataTipoFlor
import com.example.flower__shop.modelo.dataVaciarCarritoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //login y registros
    @POST("login.php")
    suspend fun login(@Body request: dataLoginRequest): Response<dataLoginResponse>

    @POST("registro.php")
    suspend fun registrarCliente(@Body request: dataRegistroRequest): Response<dataRegistroResponse>

    //Catalogo
    @GET("obtener_categorias_populares.php")
    suspend fun obtenerCategoriasPopulares(): List<dataCategoriaPopular>

    @GET("obtener_motivos.php")
    suspend fun obtenerMotivos(): List<dataMotivo>

    @GET("obtener_tipos_flor.php")
    suspend fun obtenerTiposFlor(): List<dataTipoFlor>

    @GET("obtener_productos_destacados.php")
    suspend fun obtenerProductosDestacados(): List<dataProductoItem>

    @GET("obtener_productos.php")
    suspend fun obtenerProductos(
        @Query("buscar") buscar: String? = null,
        @Query("id_categoria") idCategoria: Int? = null,
        @Query("id_motivo") idMotivo: Int? = null,
        @Query("id_tipo_flor") idTipoFlor: Int? = null,
        @Query("precio_min") precioMin: Double? = null,
        @Query("precio_max") precioMax: Double? = null
    ): List<dataProductoItem>

    @GET("detalle_producto.php")
    suspend fun obtenerDetalleProducto(@Query("id_producto") idProducto: Int): Response<dataDetalleProductoResponse>

    @POST("perfil_cliente.php")
    suspend fun perfil_cliente(@Body request: dataPerfilRequest): Response<dataPerfilResponse>

    @POST("perfil_empleado.php")
    suspend fun perfil_empleado(@Body request: dataPerfilEmpleadoRequest): Response<dataPerfilEmpleadoResponse>
    //Carrito
    @POST("agregar_al_carrito.php")
    suspend fun agregarAlCarrito(@Body request: dataAgregarCarritoRequest): Response<dataAgregarCarritoResponse>

    @GET("obtener_carrito.php")
    suspend fun obtenerCarrito(@Query("id_cliente") idCliente: Int): Response<dataObtenerCarritoResponse>

    @GET("obtener_resumen_carrito.php")
    suspend fun obtenerResumenCarrito(@Query("id_cliente") idCliente: Int): Response<dataResumenCarritoResponse>

    @POST("eliminar_del_carrito.php")
    suspend fun eliminarDelCarrito(@Body request: dataEliminarCarritoRequest): Response<dataAccionCarritoResponse>

    @POST("vaciar_carrito.php")
    suspend fun vaciarCarrito(@Body request: dataVaciarCarritoRequest): Response<dataAccionCarritoResponse>

    //Envio
    @POST("registrar_envio.php")
    suspend fun registrarEnvio(@Body request: com.example.flower__shop.modelo.dataEnvioRequest): Response<com.example.flower__shop.modelo.dataEnvioResponse>
}