package com.example.readerapp.screens.profile

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class SheetListItem(
    val title: String,
    val icon: Painter,
    val onClick: () -> Unit,
    val color: Color? = null
)
