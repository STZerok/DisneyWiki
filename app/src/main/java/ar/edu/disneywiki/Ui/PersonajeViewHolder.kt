package ar.edu.disneywiki.Ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.disneywiki.R

class PersonajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name2: TextView = itemView.findViewById(R.id.txtName2)
    val imgPersonaje: ImageView = itemView.findViewById(R.id.imageViewPersonaje)
}