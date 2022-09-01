package com.slobodyanyuk.testprojectinfotech.presentation.cities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.slobodyanyuk.testprojectinfotech.databinding.ItemCityBinding
import com.slobodyanyuk.testprojectinfotech.domain.entity.cities.ItemCity

class CityAdapter(
    var cityItems: List<ItemCity>,
    val onCityClicked: (Int, String, Float?, Float?) -> Unit,
) : RecyclerView.Adapter<CityAdapter.ItemCityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCityViewHolder =
            ItemCityViewHolder(
                ItemCityBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

    override fun onBindViewHolder(holder: ItemCityViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            val city = cityItems[position].city
            onCityClicked(city.id, city.name, city.coord?.latitude, city.coord?.longitude)
        }
        holder.binding.tvCitiesName.text = cityItems[position].city.name
        holder.binding.ivCitiesPicture.setImageBitmap(cityItems[position].bitmapImage)
    }

    override fun getItemCount(): Int = cityItems.size

    class ItemCityViewHolder(val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(newCities: List<ItemCity>) {
        val diffResult = DiffUtil.calculateDiff(CityDiffUtil(cityItems, newCities))
        cityItems = newCities
        diffResult.dispatchUpdatesTo(this)
    }
}