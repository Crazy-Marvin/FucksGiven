package rocks.poopjournal.fucksgiven.presentation.widget

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.components.CircleIconButton
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontFamily
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rocks.poopjournal.fucksgiven.MainActivity
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.presentation.ui.utils.getFormattedDate
import rocks.poopjournal.fucksgiven.presentation.ui.utils.isToday

class MyAppWidget : GlanceAppWidget() {
    override val sizeMode: SizeMode
        get() = SizeMode.Exact
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appContext = context.applicationContext
        val viewModel =
            EntryPoints.get(
                appContext,
                AppWidgetEntryPoint::class.java,
            ).getViewModel()

        provideContent {
            WidgetTheme {
                val uiState by viewModel.uiState.collectAsState()
                val list = uiState.fuckList ?: emptyList()
                Column(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .background(ImageProvider(R.drawable.app_widget_background))
                        .appWidgetBackground()
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = GlanceModifier.fillMaxWidth().padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Fucks Given", // Replace with a plain string
                            modifier = GlanceModifier.defaultWeight(),
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        CircleIconButton(
                            imageProvider = ImageProvider(R.drawable.baseline_add_24),
                            onClick = {
                                Intent(context, MainActivity::class.java).apply {
                                    flags = FLAG_ACTIVITY_NEW_TASK
                                    context.startActivity(this)
                                }
                            },
                            contentDescription = "",
                            modifier = GlanceModifier
                                .size(34.dp)
                                .background(GlanceTheme.colors.onBackground)
                                .padding(8.dp),
                            contentColor = GlanceTheme.colors.tertiary,
                            backgroundColor = GlanceTheme.colors.onBackground
                        )
                    }
                    if (list.isNotEmpty()) {
                        val sortedFucks = list.sortedWith(compareBy({ !isToday(getFormattedDate(it.date)) }, { -it.date }))

                        // Group the sorted list by date
                        val groupedFucks = sortedFucks.groupBy { getFormattedDate(it.date) }
                        LazyColumn(modifier = GlanceModifier.padding(top = 8.dp)) {
                            groupedFucks.forEach { (date, fucks) ->
                                item {
                                    Row(
                                        modifier = GlanceModifier
                                            .fillMaxWidth()
                                            .height(20.dp).background(GlanceTheme.colors.secondary),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val headerText = if (isToday(date)) {
                                            "Today"
                                        } else {
                                            date
                                        }
                                        Text(
                                            text = headerText,
                                            modifier = GlanceModifier.padding(start = 8.dp),
                                            style = TextStyle(
                                                color = GlanceTheme.colors.onBackground,
                                                fontFamily = FontFamily.SansSerif
                                            )
                                        )
                                    }
                                }
                                items(fucks) { fuck ->
                                    Column(
                                        modifier = GlanceModifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            modifier = GlanceModifier
                                                .fillMaxWidth()
                                                .height(20.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = fuck.description,
                                                modifier = GlanceModifier.padding(start = 8.dp),
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "No fucks given", // Replace with a plain string
                            modifier = GlanceModifier.padding(12.dp)
                        )
                    }
                }
            }
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface AppWidgetEntryPoint {
        fun getViewModel(): AppWidgetViewModel
    }
}





