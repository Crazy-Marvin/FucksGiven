package rocks.poopjournal.fucksgiven.view.listener

/**
 * Created by Fenil on 25-Feb-17.
 *click listener for item in list,. use it with adapters
 * @param <T> the type of model
 */
interface ItemClickListener<T> {
    fun onItemClicked(model: T)
}
