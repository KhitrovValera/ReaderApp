package com.example.readerapp.screens.my_books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readerapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MyBookTopBar(
    searchText: String = "",
    searchBook : (String) -> Unit = {}
) {

    val megaSearchText = rememberSaveable { mutableStateOf(searchText) }

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(stringResource(R.string.my_book))

                Spacer(modifier = Modifier.size(8.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    value = megaSearchText.value,
                    onValueChange = {
                        megaSearchText.value = it
                        searchBook(megaSearchText.value)
                    },
                    maxLines = 1,
                    placeholder = {
                        Text("Поиск книг…")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            stringResource(R.string.back),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                )
            }

        }
    )
}