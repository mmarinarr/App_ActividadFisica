package com.example.app_actividad_fisica

//Bloque de importaciones
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val activityList = mutableListOf<ActivitySession>()
    private lateinit var adapter: ActivitySessionAdapter


    //Declaración de un método para crear la pantalla por primera vez
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Carga el archivo activity_main para usarlo para la interfaz
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.rvActividades)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ActivitySessionAdapter(activityList)
        recyclerView.adapter = adapter

        //Conectan todos los botones, textos y campos de entrada de texto con variables
        val tvTitulo = findViewById<TextView>(R.id.tvTitulo)
        val tvActividades = findViewById<TextView>(R.id.tvActividades)
        val etActividades: EditText = findViewById(R.id.etActividades)
        val tvTipoActividad = findViewById<TextView>(R.id.tvTipoActividad)
        val etTipoActividad: EditText = findViewById(R.id.etTipoActividad)
        val tvDuracion = findViewById<TextView>(R.id.etDuracion)
        val etDuracion: EditText = findViewById(R.id.etDuracion)
        val btnProcesar = findViewById<Button>(R.id.btnProcesar)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        tvTitulo.text = "Actividad Física"

        //Procesar los datos ingresados por el usuario a través del Button
        btnProcesar.setOnClickListener {
            val actividad = etActividades.text.toString()
            val duracionTxt = etDuracion.text.toString()
            val tipo = etTipoActividad.text.toString()

            //Condición para comprobar que no hayan campos vacíos
            if (actividad.isEmpty() || duracionTxt.isEmpty() || tipo.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Condición para comprobar que la duración sea un número entero
            val duracion = duracionTxt.toIntOrNull()
            if (duracion == null) {
                Toast.makeText(this, "Duración inválida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Creación de la variable con la fecha y hora actual
            val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(Date())

            //Creación de la sesión con todos los datos ingresados
            val session =
                ActivitySession(actividad, duracion, fecha, etTipoActividad.text.toString())
            activityList.add(session)
            adapter.submitList(activityList.toList())

            etActividades.text.clear()
            etDuracion.text.clear()
        }

        //Enlace de la pantalla principal con la pantalla de registro
        btnRegistrar.setOnClickListener {
            val intent = Intent(this, RealTimeActivity::class.java)
            startActivity(intent)


        }
    }
}