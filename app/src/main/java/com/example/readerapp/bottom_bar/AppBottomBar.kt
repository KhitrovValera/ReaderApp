package com.example.readerapp.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppBottomBar(
    navController: NavController
) {
    val navListItem = listOf(
        BottomNavItem.MY_BOOKS_ITEM,
        BottomNavItem.ALL_BOOKS_ITEM,
        BottomNavItem.PROFILE_ITEM
    )

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    NavigationBar {
        navListItem.forEach { navItem ->
            NavigationBarItem(
                selected = currentDestination?.route == navItem.label,
                onClick = {
                    navController.navigate(navItem.label) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(navItem.icon, navItem.label) }
            )
        }
    }
}
