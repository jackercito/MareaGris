package com.jackercito.mareagris.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jackercito.mareagris.R
import com.jackercito.mareagris.models.Escuadra

class EscuadraListAdapter(private val onClick: (Escuadra) -> Unit) :
    ListAdapter<Escuadra, EscuadraListAdapter.EscuadraViewHolder>(EscuadraComparador()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EscuadraListAdapter.EscuadraViewHolder {
        return EscuadraViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: EscuadraListAdapter.EscuadraViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class EscuadraViewHolder(itemView: View, val onClick: (Escuadra) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val escuadraItemView: TextView = itemView.findViewById(R.id.textView)
        private var currentEscuadra: Escuadra? = null

        init {
            itemView.setOnClickListener {
                currentEscuadra?.let { onClick }
            }
        }

        fun bind(escuadra: Escuadra){
            currentEscuadra = escuadra
            escuadraItemView.text = escuadra.nombreEscuadra
        }

        companion object {
            fun create(parent: ViewGroup, onClick: (Escuadra) -> Unit): EscuadraViewHolder{
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_escuadra_item, parent, false)
                return EscuadraViewHolder(view, onClick)
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