package rocks.poopjournal.fucksgiven.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import rocks.poopjournal.fucksgiven.presentation.component.AppBar
import rocks.poopjournal.fucksgiven.presentation.component.BottomBar
import rocks.poopjournal.fucksgiven.presentation.component.BottomNavBar
import rocks.poopjournal.fucksgiven.presentation.component.HorizontalPagerView
import rocks.poopjournal.fucksgiven.presentation.viewmodel.StatsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatsScreen(navController: NavHostController, viewModel: StatsViewModel) {
    val scope = rememberCoroutineScope()
    val pager = rememberPagerState(pageCount = {
        3
    })
    var selectedPage by remember { mutableIntStateOf(0) } // Track selected page

    Scaffold(topBar = { AppBar(title = "Stats", navigate = navController) },
        bottomBar = {
            BottomNavBar(navHostController = navController, items = BottomBar.getMenuBottomItems())
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(IntrinsicSize.Min)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            pager.animateScrollToPage(0)
                        }
                        selectedPage = 0 // Update selected page
                    },
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (selectedPage == 0) MaterialTheme.colorScheme.primary else Color.Transparent,
                    ),
                    modifier = Modifier.height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selectedPage == 0) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Weekly", style = MaterialTheme.typography.bodySmall)
                }
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            pager.animateScrollToPage(1)
                        }
                        selectedPage = 1 // Update selected page

                    },
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (selectedPage == 1) MaterialTheme.colorScheme.primary else Color.Transparent,
                    ),
                    modifier = Modifier.height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selectedPage == 1) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary
                    )

                ) {
                    Text("Monthly", style = MaterialTheme.typography.bodySmall)
                }
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            pager.animateScrollToPage(2)
                        }
                        selectedPage = 2 // Update selected page

                    },
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (selectedPage == 2) MaterialTheme.colorScheme.primary else Color.Transparent,
                    ),
                    modifier = Modifier.height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selectedPage == 2) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary
                    )

                ) {
                    Text("Yearly", style = MaterialTheme.typography.bodySmall)
                }
            }

            HorizontalPagerView(
                pagerState = pager,
                viewModel = viewModel
            )
        }
    }
}
