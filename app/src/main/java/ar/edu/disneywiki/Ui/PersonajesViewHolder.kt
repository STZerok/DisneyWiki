package ar.edu.disneywiki.Ui

import android.view.View

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.disneywiki.R


class PersonajesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.txtName)
    val img: ImageView = itemView.findViewById(R.id.imgView)
}