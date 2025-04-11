package com.example.imprerativetask.navigations

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.imprerativetask.composable.list.ListScreenComposable
import com.example.imprerativetask.composable.login.LoginScreenComposable
import com.example.imprerativetask.secureStorage.SecureStorage

@Composable
fun NavGraphComposable(
    navHostController: NavHostController,
    loginSuccess : Boolean = true
) {
    val startDestination = if (loginSuccess) {
        Screens.ListScreen
    } else {
        Screens.LoginScreen
    }

    NavHost(navController = navHostController, startDestination = startDestination){

        composable<Screens.LoginScreen> {
            LoginScreenComposable(navHostController)
        }

        composable<Screens.ListScreen> {
            ListScreenComposable(navHostController)
        }

    }
}