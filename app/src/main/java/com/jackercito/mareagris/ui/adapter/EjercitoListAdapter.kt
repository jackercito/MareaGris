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
import com.jackercito.mareagris.models.REjercitoConteo

class EjercitoListAdapter(private val context: Context, private val onClick: (REjercitoConteo) -> Unit) : ListAdapter<REjercitoConteo, EjercitoListAdapter.EjercitoViewHolder> (EjercitoComparador()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjercitoViewHolder {
        return EjercitoViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: EjercitoViewHolder, position: Int){
        val current = getItem(position)
        holder.bind(current, context)
    }

    class EjercitoViewHolder(itemView: View, val onClick: (REjercitoConteo) -> Unit): RecyclerView.ViewHolder(itemView) {
        private val ejercitoItemView : TextView = itemView.findViewById(R.id.textView)
        private val imageItemView : ImageView = itemView.findViewById(R.id.img_cabecera)
        private var currentEjercito: REjercitoConteo? = null
        private val numMinisItemView: TextView = itemView.findViewById(R.id.txtMiniaturas)
        private val numMinisPintItemView: TextView = itemView.findViewById(R.id.txtCompletas)
        private val numMinisMareaItemView: TextView = itemView.findViewById(R.id.txtMareaGris)

        init {
            itemView.setOnClickListener{
                currentEjercito?.let {
                    onClick(it)
                }
            }
        }

        fun bind(ejercito: REjercitoConteo, context: Context){
            currentEjercito = ejercito
            ejercitoItemView.text = ejercito.ejercito?.nombreEjercito

            val porcentajePintado = if(ejercito.pintadas != 0){
                100 - (ejercito.pintadas!! * 100 / ejercito.totalEmpresa!!)
            } else {
                100
            }

            numMinisItemView.text = context.getString(R.string.miniaturas, ejercito.totalEmpresa)
            numMinisPintItemView.text = context.getString(R.string.completadas, ejercito.pintadas)
            numMinisMareaItemView.text = context.getString(R.string.mareagris, porcentajePintado)

            if(ejercito.ejercito?.imagen != null){
                val uri = ejercito.ejercito?.imagen
                val myUri = Uri.parse("file://$uri")
                imageItemView.setImageURI(myUri)
            }
        }

        companion object {
            fun create(parent: ViewGroup, onClick: (REjercitoConteo) -> Unit): EjercitoViewHolder {
                val view : View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_ejercito_item, parent, false)
                return EjercitoViewHolder(view, onClick)
            }
        }
    }

    class EjercitoComparador: DiffUtil.ItemCallback<REjercitoConteo>() {
        override fun areItemsTheSame(oldItem: REjercitoConteo, newItem: REjercitoConteo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: REjercitoConteo, newItem: REjercitoConteo): Boolean {
            return oldItem.ejercito?.nombreEjercito == newItem.ejercito?.nombreEjercito
        }
    }
}