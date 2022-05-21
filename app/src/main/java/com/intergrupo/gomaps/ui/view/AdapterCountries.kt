package com.intergrupo.gomaps.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.intergrupo.gomaps.databinding.ItemCountrieBinding
import com.intergrupo.gomaps.domain.model.QueryCountrie

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class AdapterCountries :

    RecyclerView.Adapter<AdapterCountries.ViewHolder>() {

    private lateinit var items: List<QueryCountrie>
    private lateinit var context: Context
    val itemClicked = MutableLiveData<QueryCountrie>()


    fun update(items: List<QueryCountrie>, context: Context) {
        this.items = items
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(parent.context)
        val binding = ItemCountrieBinding.inflate(layoutInflator)

        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(private val binding: ItemCountrieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: QueryCountrie) {

            binding.txtCountrie.text = item.countryName.uppercase()
            binding.txtCapital.text = item.capitalName.lowercase()
            binding.txtRegion.text = item.regionName.uppercase()
            binding.btnGoMaps.tag = item
            binding.btnGoMaps.setOnClickListener { queryCountrie ->
                itemClicked.postValue(queryCountrie.tag as QueryCountrie)
            }

        }

    }
}