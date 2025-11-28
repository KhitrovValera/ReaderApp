package com.example.readerapp.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.readerapp.R
import com.example.readerapp.domain.model.Book
import com.example.readerapp.ui.theme.ReaderAppTheme

@Preview(showBackground = true)
@Composable
fun BookItem(
    book: Book = Book(
        1,
        "nigga",
        "title book",
        "author book",
        "local file path",
        "uri"
    ),
    onItemClick: (Book) -> Unit = {},
    onDeleteClick: (Book) -> Unit = {},
    onDownloadClick: (Book) -> Unit = {},

) {
    ReaderAppTheme {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .height(200.dp)
                .width(160.dp)
                .clickable(onClick = { onItemClick(book) }),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                SubcomposeAsyncImage(
                    model = book.uri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    loading = {
                        Image(
                            painter = painterResource(R.drawable.ic_book),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                        )
                    },
                    error = {
                        Image(
                            painter = painterResource(R.drawable.ic_book),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                        )
                    }
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.9f)
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(4.dp)
                    ) {
                        Text(
                            book.title ?: "название не найдено",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 1
                        )

                        Text(
                            book.author ?: "название не найдено",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 1
                        )
                    }

                    IconButton(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 4.dp)
                            .align(Alignment.CenterVertically),
                        onClick = {
                            if (book.localFilePath != null) onDeleteClick(book)
                            else onDownloadClick(book)
                        }
                    ) {
                        Icon(
                            painter = if (book.localFilePath != null) rememberVectorPainter(Icons.Default.Delete)
                            else painterResource(R.drawable.ic_download),
                            null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}