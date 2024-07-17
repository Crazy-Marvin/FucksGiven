package rocks.poopjournal.fucksgiven.presentation.widget

import android.R
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.material3.ColorProviders
import rocks.poopjournal.fucksgiven.presentation.ui.theme.DarkColorScheme
import rocks.poopjournal.fucksgiven.presentation.ui.theme.LightColorScheme

@Composable
fun WidgetTheme(
    content: @Composable () -> Unit,

    ) {
    val resources = LocalContext.current.resources
    val theme = LocalContext.current.theme

    val lightBackgroundColor = Color(0xfffffbfe)
    val darkBackgroundColor = Color(0xff1c1b1f)
    val lightTextColor = Color(0xFF29A331)
    val darkTextColor = Color(0xFF29A331)
    val whiteColor = Color(0xFFFFFFFF)
    val rowBackground = Color(0xFFEEF8EF)

    GlanceTheme(
        colors =
        ColorProviders(
            light =
            lightColorScheme(
                background = lightBackgroundColor,
                onBackground = lightTextColor,
                tertiary = whiteColor,
                secondary = rowBackground

            ),
            dark =
            darkColorScheme(
                background = darkBackgroundColor,
                onBackground = darkTextColor,
                tertiary = whiteColor,
                secondary = rowBackground
            ),
        ),
        content = content,
    )
}