package rocks.poopjournal.fucksgiven.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.squareup.otto.Bus
import rocks.poopjournal.fucksgiven.helper.BusProvider
import rocks.poopjournal.fucksgiven.model.NetworkChangeEvent
import rocks.poopjournal.fucksgiven.util.Utils.isInternetAvailable

/**
 * Created by Fenil on 23-Jun-17.
 *
 * broadcast receiver for receiving connectivity changes
 * It will post [NetworkChangeEvent] whenever there connectivity state gets changed
 */
object NetworkReceiver : BroadcastReceiver() {

    private val bus: Bus = BusProvider.instance.bus

    //this flag maintains the state of last conectivity
    private var wasNetworkDisconnected = false

    override fun onReceive(context: Context, intent: Intent) {
        if (isInternetAvailable(context)) {
            if (!wasNetworkDisconnected) return
            bus.post(NetworkChangeEvent(true))
            wasNetworkDisconnected = false
        } else {
            if (wasNetworkDisconnected) return
            bus.post(NetworkChangeEvent(false))
            wasNetworkDisconnected = true
        }
    }
}