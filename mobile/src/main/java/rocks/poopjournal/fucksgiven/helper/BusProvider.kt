package rocks.poopjournal.fucksgiven.helper

import com.squareup.otto.Bus

/**
 *
 * singleton that provides event bus.
 */
class BusProvider private constructor() {
    val bus: Bus = Bus()

    companion object {
        val instance = BusProvider()
    }
}
