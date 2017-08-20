package rocks.poopjournal.fucksgiven.model

data class Entry(var id: Int, var timestamp: Long, var formattedTime: String, var place: String, var notes: String)
