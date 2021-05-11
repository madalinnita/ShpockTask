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
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shpocktask.R
import com.example.shpocktask.data.api.ApiHelper
import com.example.shpocktask.data.api.RetrofitBuilder
import com.example.shpocktask.data.models.PirateShip
import com.example.shpocktask.data.utils.CallStatus
import com.example.shpocktask.ui.shiplist.adapter.ItemClickedCallback
import com.example.shpocktask.ui.shiplist.adapter.PirateShipsAdapter
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
        initList()
    }

    private fun setupObserver() {
        viewModel.getPirateShipsResponse().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    CallStatus.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        d(TAG, resource.data.toString())
                        resource.data?.let { pirateShips -> retrieveList(pirateShips.ships.filterNotNull()) }
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

    private fun initList() {
        val linearLayoutManager = LinearLayoutManager(context)
        list_of_ships.layoutManager = linearLayoutManager
        list_of_ships.addItemDecoration(
            DividerItemDecoration(
                list_of_ships.context,
                (list_of_ships.layoutManager as LinearLayoutManager).orientation
            ).apply {
                setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.layer_divider)!!)
            }
        )
        list_of_ships.adapter =
            PirateShipsAdapter(requireContext(), emptyList(), object : ItemClickedCallback {
                override fun selectedShip(ship: PirateShip) {
                    val action = ShipListFragmentDirections.actionShipListFragmentToShipDetailsFragment(ship)
                    findNavController().navigate(action);
                }
            })
    }

    private fun retrieveList(ships: List<PirateShip>) {
        list_of_ships.smoothScrollToPosition(0)
        (list_of_ships.adapter as PirateShipsAdapter).updateList(ships)
    }

}