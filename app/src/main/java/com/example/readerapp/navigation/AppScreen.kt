package com.example.readerapp.navigation

sealed class AppScreen(
    val route: String,
    val existBottomBar: Boolean
) {
    data object ScreenSaver : AppScreen("screenSaver", false)
    data object Login : AppScreen("login", false)
    data object MyBooks : AppScreen("my books", true)
    data object AllBooks : AppScreen("all books", true)
    data object Profile : AppScreen("profile",true)
    data class Book(val bookId: Int): AppScreen(bookId.toString(), false)

}


