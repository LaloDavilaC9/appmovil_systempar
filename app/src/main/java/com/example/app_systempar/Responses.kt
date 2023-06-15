package com.example.app_systempar

import com.google.gson.annotations.SerializedName

data class AlumnoImagen(
    val type: String,
    val data: List<Int>
)

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
    val alumno_imagen: AlumnoImagen

)

data class AlumnoResponse (@SerializedName("array") var array:List<AlumnoObject>, @SerializedName("success") var success: Boolean)

data class MateriaObject(
    val materia_id: Int,
    val materia_nombre: String,
    val departamento_id: Int,
    val materia_descripcion: String,
    val materia_vigente: Int
)

data class MateriasResponse(@SerializedName("array") var array: List<MateriaObject>, @SerializedName("success") var success: Boolean)

data class SolicitudInfo(
    val materia_nombre: String,
    val tutor_nombre_completo: String?,
    val alumno_telefono: String?,
    val alumno_correo: String?,
    val solicitud_descripcion: String,
    val solicitud_tema: String,
    val solicitud_modalidad: String,
    val solicitud_lugar: String?,
    val solicitud_urgencia: String,
    val solicitud_fecha_programacion : String?
) : java.io.Serializable
data class SolicitudResponse(@SerializedName("array") var array: List<SolicitudInfo>, @SerializedName("success") var success: Boolean) : java.io.Serializable
