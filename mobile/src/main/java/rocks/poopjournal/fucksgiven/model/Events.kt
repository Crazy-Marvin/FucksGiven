package rocks.poopjournal.fucksgiven.model

import java.util.*


data class NetworkChangeEvent(var isConnected: Boolean)

data class MonthChangedEvent(var month: Calendar)