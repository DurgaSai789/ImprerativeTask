package com.example.imprerativetask.composable.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.imprerativetask.navigations.Screens

@Composable
fun LoginScreenComposable(navHostController: NavHostController,viewModel: LoginViewModel = viewModel()) {

    val context = LocalContext.current
    val loginState by viewModel.loginState.observeAsState(ApiStateHandle.Idle)


    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val emailRequestFocus = remember { FocusRequester() }
    val passwordRequestFocus = remember { FocusRequester() }

    var checked by remember { mutableStateOf(false) }

    var buttonText by rememberSaveable { mutableStateOf("Login") }

    Column(modifier = Modifier.fillMaxSize()
        .statusBarsPadding()
        .background(color = Color.Black)
        .padding(16.dp)) {

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            "Login",
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "You Must Login into your account to continue",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    if (checked) {
                        email = "admin"
                        password = "A7ge#hu&dt(wer"
                    } else {
                        email = ""
                        password = ""
                    }
                }
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = if (checked){
                    "Credentials Prefill"
                } else {
                   "Credentials known, please fill them manually"
                },
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                ),
             )
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Admin") },
            modifier = Modifier.fillMaxWidth()
                .focusRequester(emailRequestFocus)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
                .focusRequester(passwordRequestFocus)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (email.isEmpty()){
                    emailRequestFocus.requestFocus()
                    Toast.makeText(context, "Email is Should be Enter", Toast.LENGTH_SHORT).show()
                } else if (password.isEmpty()){
                    passwordRequestFocus.requestFocus()
                    Toast.makeText(context, "Password is Should be Enter", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.login(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(buttonText)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (loginState) {
            is ApiStateHandle.Loading ->{
                buttonText = "Loading..."
            }
            is ApiStateHandle.Success -> {
                navHostController.navigate(Screens.ListScreen){
                    popUpTo<Screens.LoginScreen>{
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
            is ApiStateHandle.Error   -> {
                Text(
                    text = (loginState as ApiStateHandle.Error).message,
                    color = Color.Red
                )
            }
            else                      -> {}
        }
    }
}
