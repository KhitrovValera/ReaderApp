package com.example.readerapp.bottom_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(
    val label: String,
    val icon: ImageVector
) {
    MY_BOOKS_ITEM("my books", Icons.Filled.Done),
    ALL_BOOKS_ITEM("all books", Icons.Filled.ShoppingCart),
    PROFILE_ITEM("profile", Icons.Filled.AccountCircle)
}