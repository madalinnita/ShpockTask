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

    private val FIRST_ITEM = 0
    private val OTHER_ITEMS = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PirateShipsViewHolder {
        val view = if (viewType == FIRST_ITEM) LayoutInflater.from(context)
            .inflate(R.layout.pirate_ship_header_row, parent, false) else LayoutInflater.from(
            context
        ).inflate(R.layout.pirate_ship_row, parent, false)
        return PirateShipsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PirateShipsViewHolder, position: Int) {
        val ship = ships[position]
        ship.title?.let {
            holder.ship_title.text = it
        }
        ship.description?.let {
            holder.ship_description.text = it
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
                .into(holder.ship_image)
        }
    }

    override fun getItemCount(): Int {
        return ships.size
    }

    override fun getItemViewType(position: Int): Int =
        if (position == 0) FIRST_ITEM else OTHER_ITEMS

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