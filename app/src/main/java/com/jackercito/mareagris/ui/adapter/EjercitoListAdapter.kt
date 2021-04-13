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
import com.jackercito.mareagris.models.Ejercito

class EjercitoListAdapter(private val onClick: (Ejercito) -> Unit) : ListAdapter<Ejercito, EjercitoListAdapter.EjercitoViewHolder> (EjercitoComparador()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjercitoViewHolder {
        return EjercitoViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: EjercitoViewHolder, position: Int){
        val current = getItem(position)
        holder.bind(current)
    }

    class EjercitoViewHolder(itemView: View, val onClick: (Ejercito) -> Unit): RecyclerView.ViewHolder(itemView) {
        private val ejercitoItemView : TextView = itemView.findViewById(R.id.textView)
        private val imageItemView : ImageView = itemView.findViewById(R.id.img_cabecera)
        private var currentEjercito: Ejercito? = null

        init {
            itemView.setOnClickListener{
                currentEjercito?.let {
                    onClick(it)
                }
            }
        }

        fun bind(ejercito: Ejercito){
            currentEjercito = ejercito
            ejercitoItemView.text = ejercito.nombreEjercito

            if(ejercito.imagen != null){
                var uri = ejercito.imagen
                val myUri = Uri.parse("file://$uri")
                imageItemView.setImageURI(myUri)
            }
        }

        companion object {
            fun create(parent: ViewGroup, onClick: (Ejercito) -> Unit): EjercitoViewHolder {
                val view : View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_ejercito_item, parent, false)
                return EjercitoViewHolder(view, onClick)
            }
        }
    }

    class EjercitoComparador: DiffUtil.ItemCallback<Ejercito>() {
        override fun areItemsTheSame(oldItem: Ejercito, newItem: Ejercito): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Ejercito, newItem: Ejercito): Boolean {
            return oldItem.nombreEjercito == newItem.nombreEjercito
        }
    }
}