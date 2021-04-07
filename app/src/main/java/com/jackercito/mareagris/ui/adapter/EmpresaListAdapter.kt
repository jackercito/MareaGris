package com.jackercito.mareagris.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jackercito.mareagris.R
import com.jackercito.mareagris.models.Empresa

class EmpresaListAdapter : ListAdapter<Empresa, EmpresaListAdapter.EmpresaViewHolder>(EmpresasComparador()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpresaViewHolder{
        return EmpresaViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EmpresaViewHolder, position: Int){
        val current = getItem(position)
        holder.bind(current.nombreEmpresa)
    }

    class EmpresaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val empresaItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?){
            empresaItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): EmpresaViewHolder {
                val view : View =LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
                return EmpresaViewHolder(view)
            }
        }
    }

    class EmpresasComparador: DiffUtil.ItemCallback<Empresa>(){
        override fun areItemsTheSame(oldItem: Empresa, newItem: Empresa): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Empresa, newItem: Empresa): Boolean {
            return oldItem.nombreEmpresa == newItem.nombreEmpresa
        }

    }
}