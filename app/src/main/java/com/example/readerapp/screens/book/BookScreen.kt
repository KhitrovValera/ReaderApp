package com.example.readerapp.screens.book

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.readerapp.domain.model.Book
import com.example.readerapp.screens.common.BookItem

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun BookScreen(

) {
    val testBooks = listOf(
        Book(
            id = 1,
            remoteId = "firestore_001",
            title = "The Dark Forest",
            author = "Liu Cixin",
            localFilePath = null,
            uri = "https://picsum.photos/200/300?random=1"
        ),
        Book(
            id = 2,
            remoteId = "firestore_002",
            title = "Dune",
            author = "Frank Herbert",
            localFilePath = "/storage/emulated/0/Books/dune.epub",
            uri = null
        ),
        Book(
            id = 3,
            remoteId = "firestore_003",
            title = "1984",
            author = "George Orwell",
            localFilePath = null,
            uri = "https://picsum.photos/200/300?random=3"
        ),
        Book(
            id = 4,
            remoteId = "firestore_004",
            title = "Brave New World",
            author = "Aldous Huxley",
            localFilePath = "/storage/emulated/0/Books/brave.epub",
            uri = null
        ),
        Book(
            id = 5,
            remoteId = "firestore_005",
            title = "The Hobbit",
            author = "J.R.R. Tolkien",
            localFilePath = null,
            uri = "https://picsum.photos/200/300?random=5"
        ),
        Book(
            id = 6,
            remoteId = "firestore_006",
            title = "Fahrenheit 451",
            author = "Ray Bradbury",
            localFilePath = null,
            uri = "https://picsum.photos/200/300?random=6"
        ),
        Book(
            id = 7,
            remoteId = "firestore_007",
            title = "The Name of the Wind",
            author = "Patrick Rothfuss",
            localFilePath = "/storage/emulated/0/Books/wind.epub",
            uri = null
        )
    )

//    val isRefreshing = listScreenState is CharacterListViewModel.State.Loading
    val pullRefreshState = rememberPullToRefreshState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            state = pullRefreshState,
            isRefreshing = true,
            onRefresh = {}
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(testBooks) { book ->
                    BookItem(
                        book,

                    )
                }
            }
        }
    }

}