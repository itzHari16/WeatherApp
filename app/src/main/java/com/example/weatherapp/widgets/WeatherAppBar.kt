package com.example.weatherapp.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.favorites.FavoriteViewModel



@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun WeatherAppBar(
    title: String,
    icon: ImageVector? = null,
    ismainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val showIt = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation, RectangleShape)
            .padding(10.dp)
    ) {
        //  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        val showDialog = remember { mutableStateOf(false) }
        if (showDialog.value) {
            ShowSettingDropDownMenu(showDialog = showDialog, navController)
        }
        TopAppBar(
            title = {
                Text(
                    text = title,
                    // color = MaterialTheme.colorScheme.onSecondary,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
                )
            },
            actions = {
                if (ismainScreen) {
                    IconButton(onClick = { onAddActionClicked.invoke() }) {
                        Icon(
                            imageVector = Icons.Default.Search, contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = {
                        showDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert, contentDescription = "More "
                        )

                    }
                } else Box {}

            },
            navigationIcon = {
                if (icon != null) {
                    Icon(imageVector = icon, contentDescription = null,
                        // tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.clickable {
                            onButtonClicked.invoke()
                        })
                }
                if (ismainScreen) {
                    val isAlreadyFavorite=favoriteViewModel.favlist.collectAsState().value.filter {
                        item->
                        (item.city==title.split(",")[0])
                    }
                    if (isAlreadyFavorite.isNullOrEmpty()){  Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite_Icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                favoriteViewModel.insertfavorite(
                                    Favorite(
                                        city = title.split(",")[0],
                                        country = title.split(",")[1]
                                    )
                                ).run {
                                    showIt.value=true
                                }
                            },
                        tint = Color.Red.copy(alpha = 0.9f)
                    )

                    }
                    else{ showIt.value=false
                        Box {}}
                ShowToast(context = context,showIt)
                }


            },
            // colors = TopAppBarDefaults.topAppBarColors(
            // containerColor = Color.White,
            // titleContentColor = Color.Black
            //),
            //  scrollBehavior = scrollBehavior

        )
    }
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
        if (showIt.value){
            Toast.makeText(context,"Added to fovorites",Toast.LENGTH_SHORT).show()
        }
}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>, navController: NavController
) {
    var expanded by remember { mutableStateOf(true) }
    val items = listOf("About", "Favorites", "Setting")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 65.dp, right = 10.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(150.dp)
            // .background(Color.White)
        ) {
            items.forEachIndexed() { index, text ->
                DropdownMenuItem(text = { /*TODO*/ }, onClick = {
                    expanded = false
                    showDialog.value = false

                })
                Row(Modifier) {
                    Icon(
                        imageVector = when (text) {
                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.Favorite
                            else -> Icons.Default.Settings
                        },
                        contentDescription = null,
                        // tint = Color.LightGray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = text,
                        modifier = Modifier.clickable {
                            navController.navigate(
                                when (text) {
                                    "About" -> WeatherScreens.AboutScreen.name
                                    "Favorites" -> WeatherScreens.FavouriteScreen.name
                                    else -> WeatherScreens.SettingScreen.name
                                }
                            )

                        },
                        fontWeight = FontWeight.W200
                    )
                }
            }
        }
    }
}

