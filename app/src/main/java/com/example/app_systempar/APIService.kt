package com.example.app_systempar

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url
import java.util.Objects

interface APIService {
    /* @GET
     suspend fun getTablero(@Url url:String) : Call<posicionResponse>*/

     @GET
     suspend fun login(@Url url: String) : Response<AlumnoResponse>

     @GET
     suspend fun solicitudesMaterias(@Url url : String ) : Response<MateriasResponse>

     @POST("/realizarSolicitud")
     suspend fun realizarSolicitud(@Body requestBody: RequestBody) : Response<ResponseBody>





    //Usuarios
    /*@GET

    suspend fun preguntarInfoUsuario(@Url url:String) : Response<usuarioResponse>


    //Reinicios
    @POST("/borrarInvitacion")
    suspend fun borrarInvitacion() : Response<ResponseBody>

    //Reinicios
    @POST("/borrarAtaques")
    suspend fun borrarAtaques() : Response<ResponseBody>

    //Reinicios
    @POST("/borrarJugada")
    suspend fun borrarJugada() : Response<ResponseBody>

    //Juego
    @GET
    suspend fun preguntarPosicion(@Url url:String) : Response<posicionResponse>

    @GET
    suspend fun preguntarCerteza(@Url url:String) : Response<certezaResponse>

    @POST("/atacarCelda")
    suspend fun atacar(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/dictarDefinicion")
    suspend fun definicion(@Body requestBody: RequestBody) : Response<ResponseBody>

    //Invitaciones
    @GET
    suspend fun preguntarInvitacion(@Url url:String) : Response<invitacionResponse>

    @GET
    suspend fun preguntarConfirmacion(@Url url:String) : Response<invitacionResponse>

    @POST("/invitarJugador")
    suspend fun  invitarJugador(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/confirmarInvitacion")
    suspend fun  confirmarInvitacion(@Body requestBody: RequestBody) : Response<ResponseBody>*/



}