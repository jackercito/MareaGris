package com.jackercito.mareagris.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jackercito.mareagris.R
import com.jackercito.mareagris.models.Escuadra

class EscuadraListAdapter(private val context: Context, private val onClick: (Escuadra) -> Unit) :
    ListAdapter<Escuadra, EscuadraListAdapter.EscuadraViewHolder>(EscuadraComparador()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EscuadraViewHolder {
        return EscuadraViewHolder.create(parent, context, onClick)
    }

    override fun onBindViewHolder(holder: EscuadraViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, context)
    }

    class EscuadraViewHolder(itemView: View, context: Context, private val onClick: (Escuadra) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nombreEscuadraItemView: TextView = itemView.findViewById(R.id.txtNombreEscuadra)
        private val cantidadItemView: TextView = itemView.findViewById(R.id.txtCantidad)
        private val unidadItemView: TextView = itemView.findViewById(R.id.txtUnidad)
        private val tipoItemView: TextView = itemView.findViewById(R.id.txtTipoUnidad)

        private var currentEscuadra: Escuadra? = null

        init {
            itemView.setOnClickListener {
                currentEscuadra?.let { onClick }
            }
        }

        fun bind(escuadra: Escuadra, context: Context){
            currentEscuadra = escuadra
            nombreEscuadraItemView.text = context.getString(R.string.nombre_sec_escuadra_val, escuadra.nombreEscuadra)
            cantidadItemView.text = context.getString(R.string.cantidad_unidades, escuadra.cantidad.toString())
            unidadItemView.text = context.getString(R.string.nombre_unidad, escuadra.unidad.nombre)
            tipoItemView.text = context.getString(R.string.tipo_unidad_val, escuadra.unidad.tipoUnidad)
        }

        companion object {
            fun create(parent: ViewGroup, context: Context, onClick: (Escuadra) -> Unit): EscuadraViewHolder{
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_escuadra_item, parent, false)
                return EscuadraViewHolder(view, context, onClick)
            }
        }
    }

    class EscuadraComparador: DiffUtil.ItemCallback<Escuadra>() {
        override fun areItemsTheSame(oldItem: Escuadra, newItem: Escuadra): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Escuadra, newItem: Escuadra): Boolean {
            return oldItem.nombreEscuadra == newItem.nombreEscuadra
        }
    }
}