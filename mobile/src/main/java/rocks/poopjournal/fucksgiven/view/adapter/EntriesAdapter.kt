package rocks.poopjournal.fucksgiven.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
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

    }


    class EntryHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        init {

        }
    }

    fun showOptions(postion: Int) {
    }
}
