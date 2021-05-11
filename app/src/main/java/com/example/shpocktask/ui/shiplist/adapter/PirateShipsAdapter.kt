package com.example.shpocktask.ui.shiplist.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.shpocktask.R
import com.example.shpocktask.data.models.PirateShip

class PirateShipsAdapter(
    private val context: Context,
    private var ships: List<PirateShip>,
    private val listener: ItemClickedCallback?
) : RecyclerView.Adapter<PirateShipsAdapter.PirateShipsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PirateShipsViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.pirate_ship_header_row, parent, false)
        return PirateShipsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PirateShipsViewHolder, position: Int) {
        val ship = ships[position]

        if(!ship.title.isNullOrBlank()) {
            holder.ship_title.text = ship.title
        } else {
            holder.ship_title.text = context.getString(R.string.no_title_provided)
        }

        if(!ship.description.isNullOrBlank()) {
            holder.ship_description.text = ship.description
        } else {
            holder.ship_description.text = context.getString(R.string.no_description_provided)
        }

        ship.image?.let {
            Glide.with(context)
                .load(it)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
                .placeholder(R.drawable.no_image)
                .into(holder.ship_image)
        }
    }

    override fun getItemCount(): Int {
        return ships.size
    }

    inner class PirateShipsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container_ship: ConstraintLayout = itemView.findViewById(R.id.container_ship)
        val ship_image: ImageView = itemView.findViewById(R.id.ship_image)
        val ship_title: TextView = itemView.findViewById(R.id.ship_title)
        val ship_description: TextView = itemView.findViewById(R.id.ship_description)

        init {
            container_ship.setOnClickListener {
                listener?.selectedShip(ships[adapterPosition])
                notifyDataSetChanged()
            }
        }
    }

    fun updateList(ships: List<PirateShip>) {
        this.ships = ships
        notifyDataSetChanged()
    }

}