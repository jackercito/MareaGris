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
        return EscuadraViewHolder.create(parent, context, onClick)
    }

    override fun onBindViewHolder(holder: EscuadraViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, context)
    }

    class EscuadraViewHolder(itemView: View, context: Context, private val onClick: (Escuadra) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nombreEscuadraItemView: TextView = itemView.findViewById(R.id.txtNombreEscuadra)
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
                currentEscuadra?.let { onClick }
            }
        }

        fun bind(escuadra: REscuadraProceso, context: Context){
            currentEscuadra = escuadra.escuadra
            nombreEscuadraItemView.text = context.getString(R.string.nombre_sec_escuadra_val, currentEscuadra!!.nombreEscuadra)
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

            val mg = 100 - (completadas.toDouble() / currentEscuadra!!.cantidad * 100)
            val tm = tiempo / completadas

            completadasItemView.text = context.getString(R.string.completadas, completadas.toString())
            mareaGrisItemView.text = context.getString(R.string.mareagris, mg.toString())
            tTotalItemView.text = context.getString(R.string.tiempoTotal, tiempo.toString())
            tMedioItemView.text = context.getString(R.string.tiempoMedio, tm.toString())
        }

        companion object {
            fun create(parent: ViewGroup, context: Context, onClick: (Escuadra) -> Unit): EscuadraViewHolder{
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_escuadra_item, parent, false)
                return EscuadraViewHolder(view, context, onClick)
            }
        }
    }

    class EscuadraComparador: DiffUtil.ItemCallback<REscuadraProceso>() {
        override fun areItemsTheSame(oldItem: REscuadraProceso, newItem: REscuadraProceso): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: REscuadraProceso, newItem: REscuadraProceso): Boolean {
            return oldItem.escuadra.nombreEscuadra == newItem.escuadra.nombreEscuadra
        }
    }
}