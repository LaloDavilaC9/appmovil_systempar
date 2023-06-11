package com.example.app_systempar

import com.google.gson.annotations.SerializedName

data class AlumnoObject(
    val alumno_id: Int,
    val alumno_nombre: String,
    val alumno_apellidos: String,
    val plan_id: Int,
    val alumno_semestre: Int,
    val alumno_grupo: String,
    val alumno_grupo_numero: Int,
    val alumno_telefono: String,
    val alumno_correo: String,
    val alumno_contrasena: String,
    val alumno_imagen: ByteArray

)

data class AlumnoResponse (@SerializedName("array") var array:List<AlumnoObject>, @SerializedName("success") var success: String)
