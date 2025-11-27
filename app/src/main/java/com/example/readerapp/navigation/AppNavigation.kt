package com.example.readerapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.readerapp.bottom_bar.AppBottomBar
import com.example.readerapp.screens.all_books.AllBooksTopBar
import com.example.readerapp.screens.book.BookTopBar
import com.example.readerapp.screens.login.AuthViewModel
import com.example.readerapp.screens.login.LoginScreen
import com.example.readerapp.screens.login.LoginScreenTopBar
import com.example.readerapp.screens.my_books.MyBookTopBar
import com.example.readerapp.screens.profile.ProfileTopBar

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel,
    signInWithGoogle: () -> Unit
) {
    val navController = rememberNavController()
    val currentDestinations = navController.currentBackStackEntryAsState().value?.destination


    val currentScreen = when (currentDestinations?.route) {
        "screenSaver" -> AppScreen.ScreenSaver
        "login" -> AppScreen.Login
        "my books" -> AppScreen.MyBooks
        "all books" -> AppScreen.AllBooks
        "profile" -> AppScreen.Profile
        else -> AppScreen.Book(1)
    }

    val snackBarState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            when (currentScreen) {
                AppScreen.AllBooks -> AllBooksTopBar()
                AppScreen.Login -> LoginScreenTopBar { authViewModel.onSwitchModePressed() }
                AppScreen.MyBooks -> MyBookTopBar {  }
                AppScreen.Profile -> ProfileTopBar()
                is AppScreen.Book -> BookTopBar {  }
                AppScreen.ScreenSaver -> {}
            }
        },
        bottomBar = {
            if (currentScreen.existBottomBar) AppBottomBar(navController)
        },
        snackbarHost = { SnackbarHost(snackBarState) }
    ) { values ->
        NavHost(
            navController,
            startDestination = AppScreen.Login.route,
            modifier = Modifier.padding(values)
        ) {
            composable(AppScreen.Login.route) { LoginScreen(
                authViewModel,
                signInWithGoogle,
                snackBarState
            ) { navController.navigate(AppScreen.MyBooks.route) }
            }

        }
    }

}