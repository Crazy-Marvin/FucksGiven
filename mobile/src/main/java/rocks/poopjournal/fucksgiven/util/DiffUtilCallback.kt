package rocks.poopjournal.fucksgiven.util

import android.support.v7.util.DiffUtil

/**
 * Created by Experiments on 26-Feb-17.
 */

class DiffUtilCallback<T : Identity<T>>(private val oldList: List<T>, private val newList: List<T>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].identifier == newList[newItemPosition].identifier
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}
