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
import com.jackercito.mareagris.models.Proceso

class ProcesoListAdapter (private val context: Context, private val onClick: (Proceso) -> Unit): ListAdapter<Proceso, ProcesoListAdapter.ProcesoViewHolder>(ProcesoComparador()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcesoViewHolder {
        return ProcesoViewHolder.create(parent, context, onClick)
    }

    override fun onBindViewHolder(holder: ProcesoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, context)
    }

    class ProcesoViewHolder(itemView: View, val onClick: (Proceso) -> Unit): RecyclerView.ViewHolder(itemView){

        private val nombreEscuadraViewItem : TextView = itemView.findViewById(R.id.ti_nombre_sec_escuadra)
        private var currentProceso : Proceso? = null

        init {
            itemView.setOnClickListener{
                currentProceso?.let {
                    onClick(it)
                }
            }
        }

        fun bind(proceso: Proceso, context: Context) {
            currentProceso = proceso
            nombreEscuadraViewItem.text = context.getString(R.string.cantidad_unidades,
                currentProceso!!.nombreEscuadra
            )
        }

        companion object {
            fun create(parent: ViewGroup, context: Context, onClick: (Proceso) -> Unit): ProcesoViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycleview_proceso_item, parent, false)
                return ProcesoViewHolder(view, onClick)
            }
        }
    }

    class ProcesoComparador: DiffUtil.ItemCallback<Proceso>(){
        override fun areItemsTheSame(oldItem: Proceso, newItem: Proceso): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Proceso, newItem: Proceso): Boolean {
            return oldItem.nombreEscuadra == newItem.nombreEscuadra
        }
    }
}

