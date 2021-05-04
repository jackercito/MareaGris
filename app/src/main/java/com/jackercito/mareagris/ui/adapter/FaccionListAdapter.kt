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
import com.jackercito.mareagris.models.RFaccionConteo

class FaccionListAdapter(private val context: Context, private val onClick: (RFaccionConteo) -> Unit): ListAdapter<RFaccionConteo, FaccionListAdapter.FaccionViewHolder>(FaccionComparador()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaccionViewHolder {
        return FaccionViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: FaccionViewHolder, position: Int){
        val current = getItem(position)
        holder.bind(current, context)
    }

    class FaccionViewHolder(itemView: View, val onClick: (RFaccionConteo) -> Unit): RecyclerView.ViewHolder(itemView) {
        private val faccionItemView: TextView = itemView.findViewById(R.id.textView)
        private val imageItemView: ImageView = itemView.findViewById(R.id.img_cabecera)
        private var currentFaccion: RFaccionConteo? = null
        private val numMinisItemView: TextView = itemView.findViewById(R.id.txtMiniaturas)
        private val numMinisPintItemView: TextView = itemView.findViewById(R.id.txtCompletas)
        private val numMinisMareaItemView: TextView = itemView.findViewById(R.id.txtMareaGris)

        init {
            itemView.setOnClickListener{
                currentFaccion?.let {
                    onClick(it)
                }
            }
        }

        fun bind(faccion : RFaccionConteo, context: Context){
            currentFaccion = faccion
            faccionItemView.text = faccion.faccion?.nombreFaccion

            val porcentajePintado = if(faccion.pintadas != 0){
                100 - (faccion.pintadas!! * 100 / faccion.totalEmpresa!!)
            } else {
                100
            }

            numMinisItemView.text = context.getString(R.string.miniaturas, faccion.totalEmpresa)
            numMinisPintItemView.text = context.getString(R.string.completadas, faccion.pintadas)
            numMinisMareaItemView.text = context.getString(R.string.mareagris, porcentajePintado)

            if(faccion.faccion?.imagen != null){
                val uri = faccion.faccion?.imagen
                val myUri = Uri.parse("file://$uri")
                imageItemView.setImageURI(myUri)
            }
        }

        companion object{
            fun create(parent: ViewGroup, onClick: (RFaccionConteo) -> Unit) : FaccionViewHolder{
                val view : View = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_faccion_item, parent, false)
                return FaccionViewHolder(view, onClick)
            }
        }
    }

    class FaccionComparador : DiffUtil.ItemCallback<RFaccionConteo>() {
        override fun areItemsTheSame(oldItem: RFaccionConteo, newItem: RFaccionConteo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RFaccionConteo, newItem: RFaccionConteo): Boolean {
            return oldItem.faccion?.nombreFaccion == newItem.faccion?.nombreFaccion
        }

    }
}