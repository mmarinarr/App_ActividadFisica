package com.example.app_actividad_fisica

//Importaciones necesarias para el adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//Clase Adapter para RecliclerView donde se encuentra la lista de sesiones registradas
class ActivitySessionAdapter(activityList: MutableList<ActivitySession>) :
    RecyclerView.Adapter<ActivitySessionAdapter.ViewHolder>() {

    //Lista interna que mantiene las sesiones actuales
    private val sessions = mutableListOf<ActivitySession>()

    //Función para actualizar la lista de sesiones desde fuera del Adapter
    fun submitList(list: List<ActivitySession>) {
        sessions.clear()
        sessions.addAll(list)
        notifyDataSetChanged()
    }

    //Función para crear cada ViewHolder (fila) del RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Infla la vista desde el XML
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_session, parent, false)
        return ViewHolder(view) //Devuelve el ViewHolder con la vista inflada
    }

    //Función para enlazar los datos del ViewHolder con los datos de la sesión
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sessions[position])
    }

    //Función para obtener la cantidad de elementos en la lista
    override fun getItemCount() = sessions.size

    //El ViewHolder representa cada fila del RecyclerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvActivity: TextView = itemView.findViewById(R.id.tvActivity)
        private val tvType: TextView = itemView.findViewById(R.id.tvType)
        private val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)

        //Función para enlazar los datos de la sesión con los TextViews
        fun bind(session: ActivitySession) {
            tvActivity.text = session.activity
            tvType.text = "Tipo: ${session.type}"
            tvDuration.text = "Duración: ${session.duration} min"
            tvDate.text = "Fecha: ${session.time}"
        }
    }
}
