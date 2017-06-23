package rocks.poopjournal.fucksgiven.view.adapters

import android.content.Context
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import butterknife.ButterKnife
import rocks.poopjournal.fucksgiven.view.listener.ItemClickListener

/**
 * Author : Fenil
 * Date : 08-May-16
 *
 *
 * base class for all recycler view adapters in app.<br></br>
 * sets click and long click listeners if specified.

 * @param <A> item model
 * *
 * @param <B> item view holder
 */
abstract class BaseRecyclerAdapter<A>(context: Context, val models: List<A>) : RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>() {

    var itemClickListener: ItemClickListener<A>? = null
    protected val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerAdapter.BaseViewHolder {
        val view = inflater.inflate(provideLayout(), parent, false)
        return generateViewHolder(view)
    }


    @CallSuper
    override fun onBindViewHolder(holder: BaseRecyclerAdapter.BaseViewHolder, position: Int) {
        val model = models[position]
        bind(holder, model)
        holder.itemView.setOnClickListener { itemClickListener?.onItemClicked(model) }
    }

    @LayoutRes
    protected abstract fun provideLayout(): Int

    protected abstract fun generateViewHolder(view: View): BaseRecyclerAdapter.BaseViewHolder

    protected abstract fun bind(holder: BaseRecyclerAdapter.BaseViewHolder, model: A)

    override fun getItemCount(): Int {
        return models.size
    }

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            ButterKnife.bind(this, itemView)
        }
    }

} 