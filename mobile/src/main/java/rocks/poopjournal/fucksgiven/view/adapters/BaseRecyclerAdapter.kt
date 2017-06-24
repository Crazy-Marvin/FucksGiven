package rocks.poopjournal.fucksgiven.view.adapters

import android.content.Context
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import rocks.poopjournal.fucksgiven.view.listener.ItemClickListener

/**
 *
 * base class for all recycler view adapters in app.<br></br>
 * sets click and long click listeners if specified.

 * @param [S]  model
 * @param [T] view holder
 */
abstract class BaseRecyclerAdapter<S, T : RecyclerView.ViewHolder>(context: Context, val models: List<S>) : RecyclerView.Adapter<T>() {

    var itemClickListener: ItemClickListener<S>? = null
    protected val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        val view = inflater.inflate(provideLayout(), parent, false)
        return generateViewHolder(view)
    }


    @CallSuper
    override fun onBindViewHolder(holder: T, position: Int) {
        val model = models[position]
        bind(holder, model)
        holder.itemView.setOnClickListener { itemClickListener?.onItemClicked(model) }
    }

    @LayoutRes
    protected abstract fun provideLayout(): Int

    protected abstract fun generateViewHolder(view: View): T

    protected abstract fun bind(holder: T, model: S)

    override fun getItemCount(): Int {
        return models.size
    }

} 