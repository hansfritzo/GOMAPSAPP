package com.intergrupo.gomaps.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.intergrupo.gomaps.databinding.ItemStatesBinding
import com.intergrupo.gomaps.domain.model.States
import java.util.*

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class AdapterStates :

    RecyclerView.Adapter<AdapterStates.ViewHolder>() {

    private lateinit var items: List<States>
    private lateinit var context: Context
    val itemClicked = MutableLiveData<States>()


    fun update(items: List<States>, context: Context) {
        this.items = items
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(parent.context)
        val binding = ItemStatesBinding.inflate(layoutInflator)

        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(private val binding: ItemStatesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: States) {

            binding.txtState.text = item.stateName.uppercase(Locale.getDefault())
            binding.btnGoMaps.tag = item
            binding.btnGoMaps.setOnClickListener { states ->
                itemClicked.postValue(states.tag as States)
            }

        }

    }
}