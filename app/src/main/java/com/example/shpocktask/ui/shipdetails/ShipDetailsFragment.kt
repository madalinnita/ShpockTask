package com.example.shpocktask.ui.shipdetails

import android.app.AlertDialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.shpocktask.R
import com.example.shpocktask.data.models.PirateShip
import kotlinx.android.synthetic.main.fragment_ship_details.*
import java.util.*

class ShipDetailsFragment : Fragment() {

    private val args: ShipDetailsFragmentArgs by navArgs()
    private var textToSpeech: TextToSpeech? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ship_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(args.pirateShip)

        greeting_button.setOnClickListener {
            textToSpeech?.speak(
                if (!args.pirateShip.greeting_type.isNullOrBlank()) args.pirateShip.greeting_type else Greeting.DEFAULT_AH.greeting_full,
                TextToSpeech.QUEUE_FLUSH,
                null
            )
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.pirate_ship_greeting))
                .setMessage(if (!args.pirateShip.greeting_type.isNullOrBlank()) args.pirateShip.greeting_type else Greeting.DEFAULT_AH.greeting_full)
                .setPositiveButton(getString(R.string.ok)) { _, _ -> }
                .show()
        }

        textToSpeech = TextToSpeech(requireContext()) {
            if (it != TextToSpeech.ERROR) {
                textToSpeech?.language = Locale.US;
            }
        }
    }

    private fun initViews(ship: PirateShip) {
        if (!ship.title.isNullOrBlank()) {
            ship_title.text = ship.title
        } else {
            ship_title.text = context?.getString(R.string.no_title_provided)
        }

        if (!ship.description.isNullOrBlank()) {
            ship_description.text = ship.description
        } else {
            ship_description.text = context?.getString(R.string.no_description_provided)
        }

        ship.image?.let {
            Glide.with(requireContext())
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
                .into(ship_image)
        }

        if (ship.price != null) {
            ship_price.text = getString(R.string.ship_price, ship.price)
        } else {
            ship_price.text = getString(R.string.price_not_provided)
        }
    }


    override fun onPause() {
        if (textToSpeech != null) {
            textToSpeech?.stop();
            textToSpeech?.shutdown();
        }
        super.onPause();
    }

}