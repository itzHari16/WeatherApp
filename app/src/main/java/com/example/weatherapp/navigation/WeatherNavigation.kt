package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.screens.Search.SearchScreen
import com.example.weatherapp.screens.about.AboutScreen
import com.example.weatherapp.screens.favorites.FavoriteScreen
import com.example.weatherapp.screens.setting.SettingScreen
import com.example.weatherapp.screens.main.MainScreen
import com.example.weatherapp.screens.main.MainViewModel
import com.example.weatherapp.screens.setting.SettingViewmodel
import com.example.weatherapp.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScrren.name
    ) {
        composable(WeatherScreens.SplashScrren.name) {
            WeatherSplashScreen(navController = navController)
        }
        //www.google.com/cityname="seattle"
        val route = WeatherScreens.MainScreens.name
        composable("$route/{city}", arguments = listOf(
            navArgument(name = "city") {
                type = NavType.StringType
            }
        )) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel, city = city)
            }
        }

        composable(WeatherScreens.SearchScreen.name) {
            //val mainViewModel= hiltViewModel<MainViewModel>()
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.SettingScreen.name) {
            SettingScreen(
                navController = navController
            )
        }
        composable(WeatherScreens.FavouriteScreen.name) {
            FavoriteScreen(navController = navController)
        }
    }
}