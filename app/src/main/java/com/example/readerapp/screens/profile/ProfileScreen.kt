package com.example.readerapp.screens.profile

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.readerapp.R
import com.example.readerapp.ui.theme.ReaderAppTheme

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    routeToLogin: () -> Unit
) {

    val profileState = profileViewModel.profileState.collectAsState().value

    var nameState by rememberSaveable {
        mutableStateOf("")
    }

    var photoUriState by rememberSaveable {
        mutableStateOf("")
    }

    var choseSheet by rememberSaveable {
        mutableStateOf(false)
    }

    var signOutDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val itemList = listOf(
        SheetListItem(
            "Выбрать фото из галлереи",
            painterResource(R.drawable.ic_gallery),
            { onChoseGallery() }
        ),

        SheetListItem(
            "Сделать фото",
            painterResource(R.drawable.ic_camera),
            { onChoseCamera() }
        ),

        SheetListItem(
            "Убрать фото",
            rememberVectorPainter(Icons.Default.Delete),
            { profileViewModel.changeUserData() }
        )
    )

    LaunchedEffect(profileState) {
        if (profileState is ProfileViewModel.ProfileState.Idle) {
            nameState = profileState.user.name ?: nameState
            photoUriState = profileState.user.image ?: photoUriState

        }
    }

    ReaderAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.size(56.dp))

            when (profileState) {
                is ProfileViewModel.ProfileState.Idle -> {


                    PhotoDialog(
                        signOutDialog,
                        { signOutDialog = false },
                        {
                            profileViewModel.signOut()
                            routeToLogin()
                            signOutDialog = false
                        }
                    )



                    PhotoChangeSheet(
                        choseSheet,
                        itemList
                    ) { choseSheet = false }


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SubcomposeAsyncImage(
                            model = photoUriState,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(CircleShape)
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.primary,
                                    CircleShape
                                )
                                .clickable { },
                            loading = {
                                Icon(
                                    Icons.Default.Person,
                                    null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            },
                            error = {
                                Icon(
                                    Icons.Default.Person,
                                    null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            },
                        )

                        Spacer(modifier = Modifier.size(16.dp))

                        UserDetailPlaceHolder(
                            "Имя",
                            "Введите имя",
                            nameState,
                            { nameState = it },
                            { profileViewModel.changeUserData(name = nameState) }
                        )

                        Spacer(modifier = Modifier.size(16.dp))

                        OutlinedTextField(
                            label = { Text("email", modifier = Modifier.background(Color.Transparent), color = MaterialTheme.colorScheme.inversePrimary) },
                            value = profileState.user.email,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledLabelColor = MaterialTheme.colorScheme.primary,
                                disabledTextColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = Color.Transparent,
                                disabledBorderColor = MaterialTheme.colorScheme.primary
                            ),
                            enabled = false,
                            onValueChange = { }
                        )

                        Spacer(modifier = Modifier.size(56.dp))

                        TextButton(
                            onClick = { signOutDialog = true },
                            shape = RoundedCornerShape(28.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                "Выйти из аккаунта",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Spacer(modifier = Modifier.size(4.dp))

                            Icon(
                                Icons.Default.ExitToApp,
                                null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
                ProfileViewModel.ProfileState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is ProfileViewModel.ProfileState.ProfileError -> {
                    Text(
                        profileState.message,
                        color =  MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Button(
                        onClick = { profileViewModel.getUser() },
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)

                    ) {
                        Text(
                            "Повторить",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    if (isVisible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            dismissButton = {
                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text("Нет", color = MaterialTheme.colorScheme.primary)
                }
            },
            confirmButton = {
                Button(
                    onClick = onConfirm,
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                ) {
                    Text("Да", color = MaterialTheme.colorScheme.error)
                }
            },
            title = {
                Text(
                    "Выход из аккаунта",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            text = {
                Text(
                    "Вы уверены, что хотите выйти из аккаунта?"
                )
            }
        )
    }
}

fun onChoseCamera() {

}

fun onChoseGallery() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoChangeSheet(
    isVisible: Boolean,
    itemList: List<SheetListItem>,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState
        ) {
            itemList.forEach { item ->
                ListItem(
                    headlineContent = { Text(item.title, color = MaterialTheme.colorScheme.primary) },
                    leadingContent = { Icon(painter = item.icon, null) },
                    modifier = Modifier.clickable {
                        item.onClick()
                        onDismiss()
                    }
                )
            }

        }
    }
}

@Composable
fun UserDetailPlaceHolder(
    title: String = "title",
    hint: String = "hint",
    value: String? = "text",
    onValueChange: (String) -> Unit = {},
    onChangeConfirm: (String?) -> Unit = {}
) {
    var isChange by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        label = { Text(title, modifier = Modifier.background(Color.Transparent), color = MaterialTheme.colorScheme.inversePrimary) },
        value = value ?: "",
        onValueChange = { onValueChange(it) },
        placeholder = { Text(hint, color = Color.Transparent) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = Color.Transparent,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedContainerColor = Color.Transparent,
            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
            disabledLabelColor = MaterialTheme.colorScheme.primary,
            disabledTextColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = Color.Transparent,
            disabledBorderColor = MaterialTheme.colorScheme.primary
        ),
        enabled = isChange,
        trailingIcon = {
            IconButton(
                onClick = {
                    if (isChange) onChangeConfirm(value)
                    isChange = !isChange
                }
            ) {
                Icon(
                    if (!isChange) Icons.Default.Edit else Icons.Default.Done,
                    null
                )
            }
        }
    )
}