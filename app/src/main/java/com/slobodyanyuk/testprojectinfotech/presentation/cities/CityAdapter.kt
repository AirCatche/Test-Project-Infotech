package com.slobodyanyuk.testprojectinfotech.presentation.cities

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.slobodyanyuk.testprojectinfotech.databinding.ItemCityBinding
import com.slobodyanyuk.testprojectinfotech.domain.entity.ItemCity

class CityAdapter(
    var cityItems: List<ItemCity>,
    val onCityClicked: (Int) -> Unit,
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
            cityItems[position].city.id.let { onCityClicked(it) }
        }
        holder.binding.tvCitiesName.text = cityItems[position].city.name
        holder.binding.ivCitiesPicture.setImageBitmap(getBitmapForCity(position))
    }

    override fun getItemCount(): Int = cityItems.size

    private fun getBitmapForCity(position: Int): Bitmap? = if (position % 2 == 0) {
        cityItems[position].bitmapImage.first
    } else {
        cityItems[position].bitmapImage.second
    }

    class ItemCityViewHolder(val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(newCities: List<ItemCity>) {
        val diffResult = DiffUtil.calculateDiff(CityDiffUtil(cityItems, newCities))
        cityItems = newCities
        diffResult.dispatchUpdatesTo(this)
    }
}