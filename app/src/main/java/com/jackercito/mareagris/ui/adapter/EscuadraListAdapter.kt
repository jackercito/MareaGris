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
import com.jackercito.mareagris.models.REscuadraProceso

class EscuadraListAdapter(private val context: Context, private val onClick: (Escuadra) -> Unit) :
    ListAdapter<REscuadraProceso, EscuadraListAdapter.EscuadraViewHolder>(EscuadraComparador()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EscuadraViewHolder {
        return EscuadraViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: EscuadraViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, context)
    }

    class EscuadraViewHolder(itemView: View, val onClick: (Escuadra) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val cantidadItemView: TextView = itemView.findViewById(R.id.txtCantidad)
        private val unidadItemView: TextView = itemView.findViewById(R.id.txtUnidad)
        private val tipoItemView: TextView = itemView.findViewById(R.id.txtTipoUnidad)
        private val difItemView: TextView = itemView.findViewById(R.id.txtDif)
        private val completadasItemView: TextView = itemView.findViewById(R.id.txtPintadas)
        private val mareaGrisItemView: TextView = itemView.findViewById(R.id.txtMareaGris)
        private val tTotalItemView: TextView = itemView.findViewById(R.id.txtTiempoTotal)
        private val tMedioItemView: TextView = itemView.findViewById(R.id.txtTiempoMedio)

        private var currentEscuadra: Escuadra? = null

        init {
            itemView.setOnClickListener {
                currentEscuadra?.let { onClick(it) }
            }
        }

        fun bind(escuadra: REscuadraProceso, context: Context){
            currentEscuadra = escuadra.escuadra
            cantidadItemView.text = context.getString(R.string.cantidad_unidades, currentEscuadra!!.cantidad.toString())
            unidadItemView.text = context.getString(R.string.nombre_unidad, currentEscuadra!!.unidad.nombre)
            tipoItemView.text = context.getString(R.string.tipo_unidad_val, currentEscuadra!!.unidad.tipoUnidad)
            difItemView.text = context.getString(R.string.dificultad_estimada, currentEscuadra!!.unidad.dificultadEstimada.toString())

            var completadas = 0
            var tiempo = 0

            for(proceso in escuadra.listaProcesos){
                if(proceso.estado == "Completado"){
                    completadas++
                    tiempo += proceso.tiempo
                }
            }

            val mg = if(currentEscuadra!!.cantidad != 0) 100 - (completadas / currentEscuadra!!.cantidad * 100) else 0
            val tm = if(completadas != 0) tiempo / completadas else 0

            completadasItemView.text = context.getString(R.string.completadas, completadas)
            mareaGrisItemView.text = context.getString(R.string.mareagris, mg)
            tTotalItemView.text = context.getString(R.string.tiempoTotal, tiempo.toString())
            tMedioItemView.text = context.getString(R.string.tiempoMedio, tm.toString())
        }

        companion object {
            fun create(parent: ViewGroup, onClick: (Escuadra) -> Unit): EscuadraViewHolder{
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_escuadra_item, parent, false)
                return EscuadraViewHolder(view, onClick)
            }
        }
    }

    class EscuadraComparador: DiffUtil.ItemCallback<REscuadraProceso>() {
        override fun areItemsTheSame(oldItem: REscuadraProceso, newItem: REscuadraProceso): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: REscuadraProceso, newItem: REscuadraProceso): Boolean {
            return oldItem.escuadra.unidad == newItem.escuadra.unidad
        }
    }
}