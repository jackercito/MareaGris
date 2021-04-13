package com.jackercito.mareagris.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jackercito.mareagris.R
import com.jackercito.mareagris.models.Juego

class JuegoListAdapter(private val onClick: (Juego) -> Unit) : ListAdapter<Juego, JuegoListAdapter.JuegoViewHolder>(JuegosComparador()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JuegoViewHolder {
        return JuegoViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: JuegoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class JuegoViewHolder(itemView: View, val onClick: (Juego) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val empresaItemView: TextView = itemView.findViewById(R.id.textView)
        private val imageItemView: ImageView = itemView.findViewById(R.id.img_cabecera)
        private var currentJuego: Juego? = null

        init {
            itemView.setOnClickListener {
                currentJuego?.let {
                    onClick(it)
                }
            }
        }

        fun bind(juego: Juego) {
            currentJuego = juego
            empresaItemView.text = juego.nombreJuego

            if (juego?.imagen != null) {
                val uri = juego.imagen
                val myUri = Uri.parse("file://$uri")
                imageItemView.setImageURI(myUri) //Pasar string a uri o uri a string
            }
        }

        companion object {
            fun create(parent: ViewGroup, onClick: (Juego) -> Unit): JuegoViewHolder {
                //Hay que cambiar el primer argumento a su recylcerview correspondiente (no creado aún)
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_juego_item, parent, false)
                return JuegoViewHolder(view, onClick)
            }
        }
    }

    class JuegosComparador : DiffUtil.ItemCallback<Juego>() {
        override fun areItemsTheSame(oldItem: Juego, newItem: Juego): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Juego, newItem: Juego): Boolean {
            return oldItem.nombreJuego == newItem.nombreJuego
        }

    }
}