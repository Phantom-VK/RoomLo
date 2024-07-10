package com.example.roomlo.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.roomlo.R
import com.example.roomlo.data.PreferenceHelper
import com.example.roomlo.data.User
import com.example.roomlo.ui.theme.dimens
import com.example.roomlo.viewmodels.AuthState
import com.example.roomlo.viewmodels.AuthViewModel


@Composable
fun SignUpScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var mobilenumber by remember {
        mutableStateOf("")
    }
    val passwordVisible = remember {
        mutableStateOf(false)
    }




    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current
    // Instantiate PreferenceHelper for managing SharedPreferences
    val preferenceHelper = remember { PreferenceHelper(context) }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> navController.navigate(Screen.HomeView.route)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface) //TODO Color is not showing black
            .padding(MaterialTheme.dimens.medium2),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        OutlinedTextField(
            value = mobilenumber,
            onValueChange = { mobilenumber = it },
            label = { Text("Mobile Number") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.dimens.medium2),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                errorLabelColor = MaterialTheme.colorScheme.error,

                )
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it.trim() },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.dimens.medium2),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                errorLabelColor = MaterialTheme.colorScheme.error,

                )
        )



        OutlinedTextField(
            value = password,
            onValueChange = { password = it.trim() },
            label = { Text("Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        imageVector = if (passwordVisible.value)
                            ImageVector.vectorResource(R.drawable.baseline_visibility_24)
                        else ImageVector.vectorResource(R.drawable.baseline_visibility_off_24),
                        contentDescription = "Password visibility",
                        tint = if (passwordVisible.value) colorResource(id = R.color.purple_700) else Color.Gray
                    )
                }
            },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.dimens.medium2),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                errorLabelColor = MaterialTheme.colorScheme.error,

                )
        )

        // Sign Up Button
        Button(
            onClick = {

                // Save mobile number to SharedPreferences
                preferenceHelper.userMobileNumber = mobilenumber
                authViewModel.signup(
                    User(
                        email = email,
                        password = password,
                        mobilenumber = mobilenumber
                    ),context
                )




            },
            enabled = authState != AuthState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.dimens.small3)
                .height(MaterialTheme.dimens.logoSize),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Text(text = "Create Account", color = MaterialTheme.colorScheme.primary)
        }

        // Sign Up Text
        TextButton(onClick = {
            navController.navigateUp()
        }) {
            Text(
                text = "Already have an account? Sign In.",
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        }
    }
}