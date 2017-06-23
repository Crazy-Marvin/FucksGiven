package rocks.poopjournal.fucksgiven.model.events

/**
 * Created by Experiments on 05-Mar-17.
 */

class NetworkChangedEvent(val isConnected: Boolean) {

    override fun toString(): String {
        return "NetworkChangedEvent{" +
                "isConnected=" + isConnected +
                '}'
    }
}
