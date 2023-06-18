package com.example.app_systempar

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Objects

class MainActivity : AppCompatActivity() {
    private val met = Metodos()
    private lateinit var btnIniciar : Button
    private lateinit var txtID : EditText
    private lateinit var txtContrasena : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnIniciar = findViewById(R.id.btn_entrar)
        txtID = findViewById(R.id.txtin_ID)
        txtContrasena = findViewById(R.id.txtin_contrasena)

        btnIniciar.setOnClickListener {
            verificarSesion()
        }

    }

    fun verificarSesion(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call: Response<AlumnoResponse> = met.getRetrofit().create(APIService::class.java).login("/login/${txtID.text.toString()}")
                val info = call.body() as AlumnoResponse
                if(info.array.size > 0){
                    if(info.array[0].alumno_contrasena == txtContrasena.text.toString()){
                        AlumnoManager.alumnoResponse = info
                        val intent = Intent(applicationContext, Vista_Alumno::class.java)
                        startActivity(intent)
                    }
                    else{
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Información incorrecta, vuelva a intentarlo", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }catch (e: Exception){
                println(e)
            }
        }
    }
}



//val sharedPreferences = this@MainActivity.getSharedPreferences("Datos_Alumno", Context.MODE_PRIVATE)

// val editor = sharedPreferences.edit()
//editor.putString("id_alumno", "valor")
//editor.apply()
/*if(info.array.size > 0){
    println("Compara ${info.array[0].password} VS ${txtContrasena.text.toString()}")
    if(info.array[0].password == txtContrasena.text.toString()){
        println("Inicio correcto")

        val intent = Intent(applicationContext, Vista_Alumno::class.java)
        //intent.putExtra("correoHost",txtCorreo.text.toString())
        startActivity(intent)

    }
    else{
        println("Inicio INCORRECTO")
    }
}*/


