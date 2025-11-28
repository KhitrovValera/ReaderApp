package com.example.readerapp.screens.download_book

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.readerapp.R
import com.example.readerapp.ui.theme.ReaderAppTheme


@Composable
fun DownloadBookScreen(
    downloadBookViewModel: DownloadBookViewModel
) {
    var titleState by rememberSaveable {
        mutableStateOf("")
    }

    var authorState by rememberSaveable {
        mutableStateOf("")
    }

    var photoUri by rememberSaveable {
        mutableStateOf("")
    }

    var localPath by rememberSaveable {
        mutableStateOf("")
    }

    ReaderAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.size(56.dp))

            Text(
                "Обложка книги",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.size(8.dp))

            SubcomposeAsyncImage(
                model = photoUri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(28.dp))
                    .clickable {},
                loading = {
                    Image(
                        painter = painterResource(R.drawable.ic_book),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                    )
                },
                error = {
                    Image(
                        painter = painterResource(R.drawable.ic_book),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                    )
                }
            )

            Spacer(modifier = Modifier.size(32.dp))

            DownloadTextField(
                "Название книги",
                "Введите название книги",
                titleState,
                onValueChange = { titleState = it }
            )

            Spacer(modifier = Modifier.size(8.dp))

            DownloadTextField(
                "Автор",
                "Введите автора книги",
                authorState,
                onValueChange = { authorState = it }
            )

            Spacer(modifier = Modifier.size(32.dp))

            Button(
                onClick = {
                    downloadBookViewModel.addBook(
                        authorState,
                        titleState,
                        photoUri,
                        localPath
                    )
                },
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                enabled = authorState.isNotBlank() && titleState.isNotBlank() && localPath.isNotBlank()
            ) {
                Text(
                    "Загрузить книгу",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun DownloadTextField(
    title: String,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        label = {
            Text(title,
                modifier = Modifier
                    .background(Color.Transparent),
                color = MaterialTheme.colorScheme.primary
            )
        },
        value = value,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        placeholder = { Text(hint, color = MaterialTheme.colorScheme.inversePrimary, modifier = Modifier.background(Color.Transparent)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = Color.Transparent,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.inversePrimary,
            unfocusedContainerColor = Color.Transparent,
            unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary
        ),
        onValueChange = { onValueChange(it) }
    )
}