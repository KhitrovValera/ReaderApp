package com.example.readerapp.screens.profile

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.readerapp.R
import com.example.readerapp.domain.model.User
import com.example.readerapp.ui.theme.ReaderAppTheme

@Preview(showBackground = true)
@Composable
fun ProfileScreen(
    user: User = User(
        "fsdf@gmail.com",
        "name",
        "https://lh3.googleusercontent.com/a/ACg8ocLx2is3vU3F86nVVksLc78KyzalmDVdioIKCNfgqnouDD1ONOY=s96-c"
    )
) {

    ReaderAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(top = 56.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = user.image,
                    null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CircleShape)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            CircleShape
                        )
                        .background(MaterialTheme.colorScheme.surface),
                    placeholder = rememberVectorPainter(Icons.Default.Person),
                    error = rememberVectorPainter(Icons.Default.Person)
                )

                Spacer(modifier = Modifier.size(16.dp))

                UserDetailPlaceHolder(
                    ""
                )

                Spacer(modifier = Modifier.size(16.dp))

                UserDetailPlaceHolder(
                    ""
                )


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserDetailPlaceHolder(
    title: String = "title",
    hint: String = "hint",
    value: String = "text",
    onValueChange: (String) -> Unit = {}
) {
    var isChange by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        label = { Text(title, modifier = Modifier.background(Color.Transparent)) },
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(hint, color = MaterialTheme.colorScheme.onBackground) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.background
        ),
        enabled = isChange,
        trailingIcon = {
            IconButton(
                onClick = { isChange = !isChange }
            ) {
                Icon(
                    if (!isChange) Icons.Default.Edit else Icons.Default.Done,
                    null
                )
            }
        }
    )
}