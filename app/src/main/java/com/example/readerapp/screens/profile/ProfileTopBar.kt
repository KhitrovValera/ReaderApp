package com.example.readerapp.screens.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.readerapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ProfileTopBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.profile)) },
    )
}