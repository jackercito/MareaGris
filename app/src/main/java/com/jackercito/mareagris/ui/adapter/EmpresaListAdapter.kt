package com.jackercito.mareagris.ui.adapter

import android.content.Context
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
import com.jackercito.mareagris.models.REmpresaConteo

class EmpresaListAdapter(private val context: Context, private val onClick: (REmpresaConteo) -> Unit) : ListAdapter<REmpresaConteo, EmpresaListAdapter.EmpresaViewHolder>(EmpresasComparador()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpresaViewHolder{
        return EmpresaViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: EmpresaViewHolder, position: Int){
        val current = getItem(position)
        holder.bind(current, context)

    }

    class EmpresaViewHolder(itemView: View, val onClick: (REmpresaConteo) -> Unit): RecyclerView.ViewHolder(itemView) {
        private val empresaItemView: TextView = itemView.findViewById(R.id.textView)
        private val numMinisItemView: TextView = itemView.findViewById(R.id.txtMiniaturas)
        private val numMinisPintItemView: TextView = itemView.findViewById(R.id.txtCompletas)
        private val numMinisMareaItemView: TextView = itemView.findViewById(R.id.txtMareaGris)
        private val imageItemView: ImageView = itemView.findViewById(R.id.img_cabecera)
        private var currentEmpresa: REmpresaConteo? = null

        init {
            itemView.setOnClickListener {
                currentEmpresa?.let {
                    onClick(it)
                }
            }
        }

        fun bind(empresa: REmpresaConteo?, context: Context){
            currentEmpresa = empresa
            empresaItemView.text = empresa?.empresa?.nombreEmpresa
            numMinisItemView.text = context.getString(R.string.miniaturas, empresa!!.total)
            numMinisPintItemView.text = context.getString(R.string.completadas, empresa.pintadas)
            numMinisMareaItemView.text = context.getString(R.string.mareagris, if(empresa.pintadas != 0) 1 - (empresa.total!! / empresa.pintadas!!) else 0)

            if(empresa.empresa?.imagen != null) {
                val uri = empresa.empresa?.imagen
                val myUri = Uri.parse("file://$uri")
                imageItemView.setImageURI(myUri) //Pasar string a uri o uri a string
            }
        }

        companion object {
            fun create(parent: ViewGroup, onClick: (REmpresaConteo) -> Unit): EmpresaViewHolder {
                val view : View =LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_empresa_item, parent, false)
                return EmpresaViewHolder(view, onClick)
            }
        }
    }

    class EmpresasComparador: DiffUtil.ItemCallback<REmpresaConteo>(){
        override fun areItemsTheSame(oldItem: REmpresaConteo, newItem: REmpresaConteo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: REmpresaConteo, newItem: REmpresaConteo): Boolean {
            return oldItem.empresa?.nombreEmpresa == newItem.empresa?.nombreEmpresa
        }

    }
}