package com.example.imprerativetask.composable.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.imprerativetask.composable.signout.SignOutDialog
import com.example.imprerativetask.navigations.Screens
import com.example.imprerativetask.secureStorage.SecureStorage

@Composable
fun ListScreenComposable(navHostController: NavHostController) {

    val storageKey = SecureStorage.getToken()

    val myVieModel: ListScreenViewModel = viewModel()
    myVieModel.getTransactions(storageKey ?: "")
    val listG by myVieModel.transactions.collectAsState()
    val loading by myVieModel.loading.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()
        .background(color = Color.Black)
        .statusBarsPadding()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.List,
                contentDescription = "Home Icon",
                tint = Color.White,
                modifier = Modifier.size(43.dp)
                    .padding(end = 16.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        showDialog = true
                    }
            )

            Text(
                text = "List",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(Color.Gray))

        Spacer(modifier = Modifier.height(10.dp))

        if (loading){
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        }

        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(listG){ it ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(text = "Id    : ${it.id}")
                        Text(text = "Date  : ${it.date}")
                        Text(text = "amount : ${it.amount}")

                    }
                }
            }
        }

        SignOutDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onConfirmSignOut = {
                SecureStorage.clear()
                navHostController.navigate(Screens.LoginScreen){
                    popUpTo<Screens.ListScreen>{
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )
    }
}

