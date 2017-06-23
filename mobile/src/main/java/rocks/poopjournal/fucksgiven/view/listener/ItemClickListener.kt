package rocks.poopjournal.fucksgiven.view.listener

/**
 * Created by Experiments on 25-Feb-17.

 * @param <T> the type parameter
 */
interface ItemClickListener<T> {
    fun onItemClicked(model: T)
}
