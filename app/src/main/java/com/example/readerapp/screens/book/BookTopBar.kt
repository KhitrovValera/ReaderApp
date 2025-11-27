package com.example.readerapp.screens.book

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.readerapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun BookTopBar(
    onBackArrowClick : () -> Unit = {},
    onAAButtonClick : () -> Unit = {}
) {
    TopAppBar(
        title = { Text(stringResource(R.string.download_book)) },
        actions = {
            TextButton(
                onClick = { onAAButtonClick() }
            ) {
                Text("AA")
            }
        },
        navigationIcon = {
            IconButton(
                onClick =  onBackArrowClick
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    stringResource(R.string.back)
                )
            }
        }
    )
}