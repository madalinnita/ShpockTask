package com.example.shpocktask.ui.shiplist

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.shpocktask.R
import com.example.shpocktask.data.api.ApiHelper
import com.example.shpocktask.data.api.RetrofitBuilder
import com.example.shpocktask.data.utils.CallStatus
import kotlinx.android.synthetic.main.fragment_ship_list.*
import java.util.logging.Logger

class ShipListFragment : Fragment() {
    private val TAG = this::class.java.simpleName

    private val viewModel: ShipListFragmentViewModel by viewModels {
        ShipListFragmentViewModelFactory(
            ApiHelper(RetrofitBuilder.shpockService)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ship_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPirateShips()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.getPirateShipsResponse().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    CallStatus.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        d(TAG, resource.data.toString())
                    }
                    CallStatus.ERROR -> {
                        progressBar.visibility = View.GONE
                        AlertDialog.Builder(requireContext())
                            .setTitle(getString(R.string.error_encountered))
                            .setMessage(it.message)
                            .setPositiveButton(
                                getString(R.string.ok)
                            ) { _, _ -> }
                            .show()
                    }
                    CallStatus.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

}