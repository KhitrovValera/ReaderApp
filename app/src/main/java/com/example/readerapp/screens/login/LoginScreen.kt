package com.example.readerapp.screens.login

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.readerapp.R
import com.example.readerapp.ui.theme.ReaderAppTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    authInWithGoogle: () -> Unit,
    snackBarState: SnackbarHostState,
    routeToMain: () -> Unit
) {
    val authScope = rememberCoroutineScope()

    val authState = authViewModel.authState.collectAsState().value

    val errorVisibility = authState is AuthViewModel.AuthState.AuthError

    val isLoading = authState == AuthViewModel.AuthState.Loading

    val isRegister = authViewModel.isRegister.collectAsState().value

    var emailState by rememberSaveable {
        mutableStateOf("")
    }

    var passwordState by rememberSaveable {
        mutableStateOf("")
    }

    var registerPasswordState by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(authState) {
        if (authState is AuthViewModel.AuthState.AuthError) {
            snackBarState.showSnackbar(
                authState.message ?: "Неизвестная ошибка"
            )
        }

        if (authState is AuthViewModel.AuthState.Success) {
            routeToMain()
        }
    }


    ReaderAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                if (errorVisibility) {
                    IconButton(
                        onClick = {
                            authScope.launch {
                                snackBarState.showSnackbar(
                                    authState.message ?: "Неизвестная ошибка"
                                )
                                Log.d("fsdfsdfsdfsfds", "$authState")
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            null
                        )
                    }
                }

                AuthTextField(
                    "email",
                    "Введите адрес электронной почты",
                    emailState,
                    { emailState = it }
                )

                Spacer(modifier = Modifier.size(8.dp))

                AuthTextField(
                    "пароль",
                    "Введите пароль",
                    passwordState,
                    { passwordState = it },
                    true

                )

                if (isRegister) {
                    Spacer(modifier = Modifier.size(8.dp))

                    AuthTextField(
                        "подтверждение пароля",
                        "Введите тот же пароль",
                        registerPasswordState,
                        { registerPasswordState = it },
                        true
                    )
                }


                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = { authViewModel.authWithEmail(emailState, passwordState, registerPasswordState) },
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    enabled = !isLoading

                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        val text = if (!isRegister) "Войти" else "Зарегестрироваться"
                        Text(
                            text,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                }

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    "или",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                )

                TextButton(
                    onClick = { authInWithGoogle() },
                    shape = RoundedCornerShape(28.dp),
                    contentPadding = PaddingValues(0.dp),
                    enabled = !isLoading,
                    modifier = Modifier.alpha(if (isLoading) 0.6f else 1f)
                ) {
                    Icon(painterResource(
                        R.drawable.ic_googlle),
                        null,
                        tint = Color.Unspecified
                    )
                    Text(
                        "Войти при помощи Google",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun AuthTextField(
    title: String,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    isPassword: Boolean = false
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

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
        visualTransformation = if (isPassword && !passwordVisibility) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = {
            if (isPassword) {
                IconButton(
                    onClick = { passwordVisibility = !passwordVisibility }
                ) {
                    Icon(
                        painter = painterResource(if (passwordVisibility)  R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                        contentDescription = "Показать пароль"
                    )
                }
            }
        }
    )
}