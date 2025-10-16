package rocks.poopjournal.fucksgiven.presentation.widget

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionStartActivity
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
import rocks.poopjournal.fucksgiven.data.FuckData
import rocks.poopjournal.fucksgiven.data.FuckRepository
import rocks.poopjournal.fucksgiven.data.SecureStorage
import rocks.poopjournal.fucksgiven.presentation.ui.utils.getFormattedDate
import rocks.poopjournal.fucksgiven.presentation.ui.utils.isToday

class MyAppWidget : GlanceAppWidget() {
    override val sizeMode: SizeMode
        get() = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appContext = context.applicationContext
        val entryPoint = EntryPoints.get(appContext, AppWidgetEntryPoint::class.java)
        val repository = entryPoint.repository()
        val secureStorage = entryPoint.getSecureStorage()
        val isLockedFlow = secureStorage.passwordProtectionEnabledFlow
        val fucksFlow = repository.getAllFucks()
        provideContent {
            WidgetTheme {
                val isLocked by isLockedFlow.collectAsState()
                val fucks by fucksFlow.collectAsState(emptyList())
                if (isLocked) {
                    // Show a locked state if protection is ON
                    LockedWidgetContent(context = appContext)
                } else {
                    UnlockedWidgetContent(context = appContext, list = fucks)
                }
            }
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface AppWidgetEntryPoint {
        fun repository(): FuckRepository

        fun getSecureStorage(): SecureStorage
    }
}

/**
 * Composable for the locked widget view.
 */
@Composable
private fun LockedWidgetContent(context: Context) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ImageProvider(R.drawable.app_widget_background))
            .appWidgetBackground()
            .clickable( // Make the entire widget clickable
                actionStartActivity(
                    Intent(context, MainActivity::class.java).apply {
                        flags = FLAG_ACTIVITY_NEW_TASK
                    }
                )
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            provider = ImageProvider(R.drawable.ic_lock), // Your new lock icon
            contentDescription = stringResource(R.string.widget_locked),
            modifier = GlanceModifier.size(48.dp)
        )
        Text(
            text = stringResource(R.string.tap_to_unlock),
            modifier = GlanceModifier.padding(top = 8.dp),
            style = TextStyle(color = GlanceTheme.colors.onBackground)
        )
    }
}


@Composable
fun UnlockedWidgetContent(context: Context, list: List<FuckData>) {
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
                text = stringResource(R.string.app_name), // Replace with a plain string
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
            val sortedFucks =
                list.sortedWith(compareBy({ !isToday(getFormattedDate(it.date)) }, { -it.date }))

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
                                stringResource(R.string.today)
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
                text = stringResource(R.string.no_fucks), // Replace with a plain string
                modifier = GlanceModifier.padding(12.dp)
            )
        }
    }
}

