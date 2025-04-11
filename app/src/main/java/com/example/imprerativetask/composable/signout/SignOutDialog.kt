package com.example.imprerativetask.composable.signout

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SignOutDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirmSignOut: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Sign Out") },
            text = { Text("Are you sure you want to sign out?") },
            confirmButton = {
                TextButton(onClick = {
                    onConfirmSignOut()
                    onDismiss()
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("No")
                }
            }
        )
    }
}
