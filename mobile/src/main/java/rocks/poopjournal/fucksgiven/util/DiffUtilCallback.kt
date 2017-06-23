package rocks.poopjournal.fucksgiven.util

import android.support.v7.util.DiffUtil

/**
 * Created by Fenil on 26-Feb-17.
 *
 * to refresh list in efficient way while working with recyclerview
 * calculates only difference in payload using [Identity] and updates only those items which are changed
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
