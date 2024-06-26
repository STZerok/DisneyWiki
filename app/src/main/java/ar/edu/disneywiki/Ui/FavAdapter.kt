package ar.edu.disneywiki.Ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.disneywiki.Model.Personaje
import ar.edu.disneywiki.R
import com.squareup.picasso.Picasso

class FavAdapter  : RecyclerView.Adapter<PersonajesViewHolder>(){
    var items: MutableList<Personaje> = ArrayList<Personaje>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.personaje_item, parent,false)
        return PersonajesViewHolder(view)
    }
    override fun onBindViewHolder(holder: PersonajesViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        if (item.imageUrl.isNotEmpty()) {
            Picasso.get()
                .load(item.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.img)
        } else {
            holder.img.setImageResource(R.drawable.placeholder_image)
        }
        holder.itemView.setOnClickListener {
            val id = items[position]._id
            val intent = Intent(holder.itemView.context, PersonajeActivity::class.java)
            intent.putExtra("id",id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun Update(lista: MutableList<Personaje>){
        items = lista
        this.notifyDataSetChanged()
    }


}