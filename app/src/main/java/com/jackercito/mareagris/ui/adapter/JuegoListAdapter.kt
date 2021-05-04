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
import com.jackercito.mareagris.models.RJuegoConteo

class JuegoListAdapter(private val context: Context, private val onClick: (RJuegoConteo) -> Unit) : ListAdapter<RJuegoConteo, JuegoListAdapter.JuegoViewHolder>(JuegosComparador()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JuegoViewHolder {
        return JuegoViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: JuegoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, context)
    }

    class JuegoViewHolder(itemView: View, val onClick: (RJuegoConteo) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val empresaItemView: TextView = itemView.findViewById(R.id.textView)
        private val imageItemView: ImageView = itemView.findViewById(R.id.img_cabecera)
        private var currentJuego: RJuegoConteo? = null
        private val numMinisItemView: TextView = itemView.findViewById(R.id.txtMiniaturas)
        private val numMinisPintItemView: TextView = itemView.findViewById(R.id.txtCompletas)
        private val numMinisMareaItemView: TextView = itemView.findViewById(R.id.txtMareaGris)

        init {
            itemView.setOnClickListener {
                currentJuego?.let {
                    onClick(it)
                }
            }
        }

        fun bind(juego: RJuegoConteo, context: Context) {
            currentJuego = juego
            empresaItemView.text = juego.juego?.nombreJuego
            val porcentajePintado = if(juego.pintadas != 0){
                100 - (juego.pintadas!! * 100 / juego.totalEmpresa!!)
            } else {
                100
            }

            numMinisItemView.text = context.getString(R.string.miniaturas, juego.totalEmpresa)
            numMinisPintItemView.text = context.getString(R.string.completadas, juego.pintadas)
            numMinisMareaItemView.text = context.getString(R.string.mareagris, porcentajePintado)

            if (juego.juego?.imagen != null) {
                val uri = juego.juego?.imagen
                val myUri = Uri.parse("file://$uri")
                imageItemView.setImageURI(myUri) //Pasar string a uri o uri a string
            }
        }

        companion object {
            fun create(parent: ViewGroup, onClick: (RJuegoConteo) -> Unit): JuegoViewHolder {
                //Hay que cambiar el primer argumento a su recylcerview correspondiente (no creado aún)
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_juego_item, parent, false)
                return JuegoViewHolder(view, onClick)
            }
        }
    }

    class JuegosComparador : DiffUtil.ItemCallback<RJuegoConteo>() {
        override fun areItemsTheSame(oldItem: RJuegoConteo, newItem: RJuegoConteo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RJuegoConteo, newItem: RJuegoConteo): Boolean {
            return oldItem.juego?.nombreJuego  == newItem.juego?.nombreJuego
        }

    }
}