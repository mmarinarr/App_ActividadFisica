package com.example.app_actividad_fisica

//Importaciones necesarias
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.graphics.Color
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs
import kotlin.math.sqrt

//Clase RealTimeActivity que hereda de AppCompatActivity y implementa SensorEventListener
class RealTimeActivity : AppCompatActivity(), SensorEventListener {

    //Declaración de variables
    private lateinit var tvShakeValue: TextView
    private lateinit var tvShakeLevel: TextView
    private lateinit var layoutRoot: LinearLayout


    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private var lastAcceleration = 0f
    private var currentAcceleration = 0f
    private var shakeIntensity = 0f

    //Metodo onCreate que se llama al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime)

        //Inicialización de variables
        tvShakeValue = findViewById(R.id.tvShakeValue)
        tvShakeLevel = findViewById(R.id.tvShakeLevel)
        layoutRoot = findViewById(R.id.layoutRoot)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH

        //Botón para volver a la actividad principal
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        btnVolver.setOnClickListener { finish() }


    }

    //Métodos de la interfaz SensorEventListener
    override fun onSensorChanged(evento: SensorEvent) {

        //Condicional para verificar que el evento provenga del acelerómetro
        if (evento.sensor.type == Sensor.TYPE_ACCELEROMETER){

            // Valores de los tres ejes X, Y, Z del sensor
            val x = evento.values[0]
            val y = evento.values[1]
            val z = evento.values[2]

            //Cálculo de la aceleración
            val acceleration = sqrt(x * x + y * y + z * z)

            lastAcceleration = currentAcceleration
            currentAcceleration = acceleration

            //Cálculo de la intensidad de movimiento
            val delta = currentAcceleration - lastAcceleration
            shakeIntensity = abs(delta)

            // Muestra el valor numérico de la intensidad en la pantalla
            tvShakeValue.text = String.format("%.2f", shakeIntensity)

            //Condicionales para verificar el nivel de movimiento, y modificar el color de fondo y texto
            val (levelText, color) = when {
                shakeIntensity < 0.1f -> "Sin Movimiento" to Color.LTGRAY
                shakeIntensity < 2f -> "Movimiento suave" to Color.GREEN
                shakeIntensity < 4f -> "Movimiento medio" to Color.YELLOW
                else -> "Movimiento intenso" to Color.RED
            }

            tvShakeLevel.text = levelText
            layoutRoot.setBackgroundColor(color)

        }

    }

    //Metodo obligatorio de SensorEventListener para cuando cambia la precisión del sensor
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    //Metodo que registra el listener del acelerómetro para recibir datos en tiempo real
    override fun onResume() {
        super.onResume()
        accelerometer?.also { sensor ->
            sensorManager.registerListener(
                this,
                sensor,
                //Actualiza la interfaz a una velocidad moderada
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    override fun onPause(){
        super.onPause()
        sensorManager.unregisterListener(this)
    }



}