package rocks.poopjournal.fucksgiven.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import rocks.poopjournal.fucksgiven.presentation.ui.utils.THETABLE_TABLENAME

@Entity(tableName = THETABLE_TABLENAME)
data class FuckData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description : String,
    val date: Long
)
