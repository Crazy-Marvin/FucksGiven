package rocks.poopjournal.fucksgiven.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.squareup.otto.Bus
import rocks.poopjournal.fucksgiven.helper.BusProvider
import rocks.poopjournal.fucksgiven.model.events.NetworkChangedEvent
import rocks.poopjournal.fucksgiven.util.Utils.isInternetAvailable

/**
 * Created by Experiments on 23-Jun-17.
 */
object NetworkReceiver : BroadcastReceiver() {

    private val bus: Bus = BusProvider.instance.bus
    private var wasNetworkDisconnected = false
    override fun onReceive(context: Context, intent: Intent) {
        if (isInternetAvailable(context)) {
            if (!wasNetworkDisconnected) return
            bus.post(NetworkChangedEvent(true))
            wasNetworkDisconnected = false
        } else {
            if (wasNetworkDisconnected) return
            bus.post(NetworkChangedEvent(false))
            wasNetworkDisconnected = true
        }
    }
}