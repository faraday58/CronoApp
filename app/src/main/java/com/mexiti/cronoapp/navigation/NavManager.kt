package com.mexiti.cronoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mexiti.cronoapp.ui.views.AddView
import com.mexiti.cronoapp.ui.views.HomeView
import com.mexiti.cronoapp.viewmodel.CronometroViewModel
import com.mexiti.cronoapp.viewmodel.DataViewModel

@Composable
fun NavManager(cronometroVM:CronometroViewModel ) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            HomeView(navController)
        }
        composable("AddView") {
            AddView(navController,cronometroVM)
        }

    }
}
