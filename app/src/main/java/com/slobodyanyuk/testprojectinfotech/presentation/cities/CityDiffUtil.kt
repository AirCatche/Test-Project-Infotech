package com.slobodyanyuk.testprojectinfotech.presentation.cities

import androidx.recyclerview.widget.DiffUtil
import com.slobodyanyuk.testprojectinfotech.domain.entity.ItemCity

class CityDiffUtil(
    private val oldList: List<ItemCity>,
    private val newList: List<ItemCity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].city.id == newList[newItemPosition].city.id &&
                    oldList[oldItemPosition].bitmapImage == newList[newItemPosition].bitmapImage

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = when {
        oldList[oldItemPosition].city.id != newList[newItemPosition].city.id -> false
        oldList[oldItemPosition].city.name != newList[newItemPosition].city.name -> false
        oldList[oldItemPosition].city.country != newList[newItemPosition].city.country -> false
        oldList[oldItemPosition].city.coord != newList[newItemPosition].city.coord -> false
        oldList[oldItemPosition].bitmapImage != newList[newItemPosition].bitmapImage -> false
        else -> true
    }
    // TODO Improvement (Implement payload for correct work and avoiding blinking)
}