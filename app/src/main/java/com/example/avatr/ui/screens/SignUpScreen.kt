package com.example.avatr.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.avatr.R
import com.example.avatr.ui.components.CustomButton1
import com.example.avatr.ui.navigation.NavigationDestination
import com.example.avatr.ui.navigation.navigateTo
import com.example.avatr.ui.viewmodels.AuthState
import com.example.avatr.ui.viewmodels.AuthViewModel

object SignUpDestination : NavigationDestination {
    override val route = "signup"
    override val titleRes = R.string.sign_up
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {

    var isFocused by remember { mutableStateOf(false) }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Authenticated -> navController.navigateTo(HomeDestination)
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier.padding(
                top = dimensionResource(R.dimen.extra_large_padding),
                bottom = dimensionResource(R.dimen.large_padding),
                start = dimensionResource(R.dimen.large_padding),
                end = dimensionResource(R.dimen.large_padding)
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.displayLarge
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                shape = RoundedCornerShape(8.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    cursorColor = MaterialTheme.colorScheme.onPrimary
                ),
                placeholder = {
                    Text(
                        "Email",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xff494d5a),
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.button_height))
                    .border(4.dp, Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused },
                enabled = true,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                shape = RoundedCornerShape(8.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    cursorColor = MaterialTheme.colorScheme.onPrimary
                ),
                placeholder = {
                    Text(
                        "Password",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xff494d5a),
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.button_height))
                    .border(4.dp, Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused },
                enabled = true,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton1(
                R.string.create_account,
                imageVector = Icons.AutoMirrored.Filled.Login,
                action = { authViewModel.signup(email, password) },
                enable = authState.value != AuthState.Loading
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { navController.navigateTo(LoginDestination) }
            ) {
                Text(
                    text = "Already have an account ?, Login!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}