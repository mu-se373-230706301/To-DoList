package com.example.todo1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApp()
        }
    }
}

@Composable
fun TodoApp() {
    var todoItems by remember { mutableStateOf(listOf("Task 1", "Task 2", "Task 3")) }
    var newItemText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "To-Do List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = newItemText,
            onValueChange = { newItemText = it },
            label = { Text("Enter a new task") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (newItemText.isNotBlank()) {
                    todoItems = todoItems + newItemText
                    newItemText = ""
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp)
        ) {
            Text("Add Task")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column {
            todoItems.forEachIndexed { index, item ->
                TodoItem(text = item) {
                    todoItems = todoItems.toMutableList().also {
                        it.removeAt(index)
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItem(text: String, onItemClicked: () -> Unit) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = { isChecked = !isChecked })
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = null // null lambda to prevent Checkbox from being interactive
        )
        Text(
            text = text,
            style = if (isChecked) MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            else MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f).padding(start = 8.dp)
        )
        IconButton(onClick = onItemClicked) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}