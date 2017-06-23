package rocks.poopjournal.fucksgiven.helper

import com.squareup.otto.Bus

/**
 * Created by Fenil on 05-Mar-17.
 *
 * singleton that provides event bus.
 */
class BusProvider private constructor() {
    val bus: Bus = Bus()

    companion object {
        val instance = BusProvider()
    }
}
