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

class EmpresaListAdapter : ListAdapter<Empresa, EmpresaListAdapter.EmpresaViewHolder>(EmpresasComparador()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpresaViewHolder{
        return EmpresaViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EmpresaViewHolder, position: Int){
        val current = getItem(position)
        holder.bind(current.nombreEmpresa, current.imagen)
    }

    class EmpresaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val empresaItemView: TextView = itemView.findViewById(R.id.textView)
        private val imageItemView: ImageView = itemView.findViewById(R.id.img_cabecera)

        fun bind(text: String?, url: String?){
            empresaItemView.text = text

            if(url != null) {
                val myUri = Uri.parse("file://$url")
                imageItemView.setImageURI(myUri) //Pasar string a uri o uri a string
            }

        }

        companion object {
            fun create(parent: ViewGroup): EmpresaViewHolder {
                val view : View =LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_empresa_item, parent, false)
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