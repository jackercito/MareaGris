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
import com.jackercito.mareagris.models.Faccion

class FaccionListAdapter(private val onClick: (Faccion) -> Unit): ListAdapter<Faccion, FaccionListAdapter.FaccionViewHolder>(FaccionComparador()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaccionViewHolder {
        return FaccionViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: FaccionViewHolder, position: Int){
        val current = getItem(position)
        holder.bind(current)
    }

    class FaccionViewHolder(itemView: View, val onClick: (Faccion) -> Unit): RecyclerView.ViewHolder(itemView) {
        private val faccionItemView: TextView = itemView.findViewById(R.id.textView)
        private val imageItemView: ImageView = itemView.findViewById(R.id.img_cabecera)
        private var currentFaccion: Faccion? = null

        init {
            itemView.setOnClickListener{
                currentFaccion?.let {
                    onClick(it)
                }
            }
        }

        fun bind(faccion : Faccion){
            currentFaccion = faccion
            faccionItemView.text = faccion.nombreFaccion

            if(faccion.imagen != null){
                val uri = faccion.imagen
                val myUri = Uri.parse("file://$uri")
                imageItemView.setImageURI(myUri)
            }
        }

        companion object{
            fun create(parent: ViewGroup, onClick: (Faccion) -> Unit) : FaccionViewHolder{
                val view : View = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_faccion_item, parent, false)
                return FaccionViewHolder(view, onClick)
            }
        }
    }

    class FaccionComparador : DiffUtil.ItemCallback<Faccion>() {
        override fun areItemsTheSame(oldItem: Faccion, newItem: Faccion): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Faccion, newItem: Faccion): Boolean {
            return oldItem.nombreFaccion == newItem.nombreFaccion
        }

    }
}