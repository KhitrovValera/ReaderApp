package com.example.readerapp.screens.login

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.readerapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenTopBar(
    onRegisterButtonClick : () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(R.string.login_in_acc)) },
        actions = {
            TextButton(
                onClick = { onRegisterButtonClick() }
            ) {
                Text(stringResource(R.string.register))
            }
        }
    )
}