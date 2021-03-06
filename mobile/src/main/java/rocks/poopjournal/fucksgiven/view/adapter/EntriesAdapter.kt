package rocks.poopjournal.fucksgiven.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_entries.view.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.model.Entry

class EntriesAdapter(context: Context, models: List<Entry>)
    : BaseRecyclerAdapter<Entry, EntriesAdapter.EntryHolder>(context, models) {

    override fun provideLayout(): Int {
        return R.layout.item_entries
    }

    override fun generateViewHolder(view: View): EntryHolder {
        return EntryHolder(view)
    }

    override fun bind(holder: EntryHolder, model: Entry) {
        holder.itemView.tvTime.text = model.formattedTime
        holder.itemView.tvLocation.text = model.place
        holder.itemView.tvNotes.text = model.notes
    }


    class EntryHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    fun showOptions(position: Int) {

    }
}
