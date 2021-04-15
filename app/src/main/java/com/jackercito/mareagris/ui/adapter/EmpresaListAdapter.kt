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
import com.jackercito.mareagris.models.Empresa


class EmpresaListAdapter(private val onClick: (Empresa) -> Unit) : ListAdapter<Empresa, EmpresaListAdapter.EmpresaViewHolder>(EmpresasComparador()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpresaViewHolder{
        return EmpresaViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: EmpresaViewHolder, position: Int){
        val current = getItem(position)
        holder.bind(current)

    }

    class EmpresaViewHolder(itemView: View, val onClick: (Empresa) -> Unit): RecyclerView.ViewHolder(itemView) {
        private val empresaItemView: TextView = itemView.findViewById(R.id.textView)
        private val imageItemView: ImageView = itemView.findViewById(R.id.img_cabecera)
        private var currentEmpresa: Empresa? = null

        init {
            itemView.setOnClickListener {
                currentEmpresa?.let {
                    onClick(it)
                }
            }
        }

        fun bind(empresa: Empresa?){
            currentEmpresa = empresa
            empresaItemView.text = empresa?.nombreEmpresa

            if(empresa?.imagen != null) {
                val uri = empresa.imagen
                val myUri = Uri.parse("file://$uri")
                imageItemView.setImageURI(myUri) //Pasar string a uri o uri a string
            }
        }

        companion object {
            fun create(parent: ViewGroup, onClick: (Empresa) -> Unit): EmpresaViewHolder {
                val view : View =LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_empresa_item, parent, false)
                return EmpresaViewHolder(view, onClick)
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