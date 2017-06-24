package rocks.poopjournal.fucksgiven.util

/**
 * works as a differentiator of items.
 * Each implementation will provide their identity it can be of any type.
 */

interface Identity<T> {
    val identifier: T
}
