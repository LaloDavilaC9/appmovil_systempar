package com.example.app_systempar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.app_systempar.databinding.ActivityProgramarSolicitudBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Programar_Solicitud : AppCompatActivity() {
    private lateinit var binding: ActivityProgramarSolicitudBinding
    private var selectedDate: Calendar = Calendar.getInstance()
    private var selectedTime: Calendar = Calendar.getInstance()
    private val met = Metodos()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programar_solicitud)
        binding = ActivityProgramarSolicitudBinding.inflate(layoutInflater)

        val view = binding.root
        val solicitudInfo = intent.getSerializableExtra("info") as SolicitudInfo

        binding.txtInfoTutor.text = solicitudInfo.tutor_nombre_completo
        binding.txtInfoAsesoria.text = solicitudInfo.materia_nombre



        binding.btnFecha.setOnClickListener {
            showDatePicker()
        }
        binding.btnHora.setOnClickListener {
            showTimePicker()
        }

        binding.btnRegresar.setOnClickListener {
            val intent = Intent(applicationContext, Vista_Tutor::class.java)
            startActivity(intent)
        }

        binding.btnProgramar.setOnClickListener {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

            val fechaString = dateFormat.format(selectedDate.time)
            val horaString = timeFormat.format(selectedTime.time)
            val fechaHoraString = "$fechaString $horaString"

            val jsonObject = JSONObject()
            jsonObject.put("fecha_programacion", fechaHoraString)
            jsonObject.put("solicitud_id", solicitudInfo.solicitud_id)
            jsonObject.put("lugar", binding.txintLugar.text.toString())

            //Se convierte el objeto Json a String
            var jsonString = jsonObject.toString()

            val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
            val retrofit = met.getRetrofit()


            val service = retrofit.create(APIService::class.java)
            CoroutineScope(Dispatchers.IO).launch {

                val response = service.programarSolicitud(requestBody)
                withContext(Dispatchers.Main) {
                    //El siguiente IF controla si se pudo conectar a la API o no
                    if (response.isSuccessful) {
                        // Convert raw JSON to pretty JSON using GSON library
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(
                            JsonParser.parseString(
                                response.body()?.string()
                            )
                        )
                        val intent = Intent(applicationContext, Vista_Tutor::class.java)
                        startActivity(intent)
                    }
                }
            }
        }

        setContentView(view)
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                selectedDate.set(year, month, dayOfMonth)
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
            },
            selectedTime.get(Calendar.HOUR_OF_DAY),
            selectedTime.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }


}