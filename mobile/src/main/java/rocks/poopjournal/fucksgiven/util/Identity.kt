package rocks.poopjournal.fucksgiven.util

/**
 * Created by Fenil on 26-Feb-17.
 * works as a differentiator of items.
 * Each implementation will provide their identity it can be of any type.
 */

interface Identity<T> {
    val identifier: T
}
