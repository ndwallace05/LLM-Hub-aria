package com.llmhub.llmhub.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.llmhub.llmhub.data.PersonaEntity
import com.llmhub.llmhub.viewmodels.PersonaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonaManagementScreen(
    navController: NavController,
    personaViewModel: PersonaViewModel
) {
    val personas by personaViewModel.allPersonas.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedPersona by remember { mutableStateOf<PersonaEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Personas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                selectedPersona = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Persona")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (personas.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No personas created yet. Tap '+' to add one.")
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(personas) { persona ->
                        PersonaListItem(
                            persona = persona,
                            onEdit = {
                                selectedPersona = it
                                showDialog = true
                            },
                            onDelete = {
                                personaViewModel.delete(it)
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        PersonaEditDialog(
            persona = selectedPersona,
            onDismiss = { showDialog = false },
            onSave = {
                if (it.id == 0) {
                    personaViewModel.insert(it)
                } else {
                    personaViewModel.update(it)
                }
                showDialog = false
            }
        )
    }
}

@Composable
fun PersonaListItem(
    persona: PersonaEntity,
    onEdit: (PersonaEntity) -> Unit,
    onDelete: (PersonaEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onEdit(persona) }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = persona.name, style = MaterialTheme.typography.titleMedium)
            Row {
                IconButton(onClick = { onEdit(persona) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { onDelete(persona) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

@Composable
fun PersonaEditDialog(
    persona: PersonaEntity?,
    onDismiss: () -> Unit,
    onSave: (PersonaEntity) -> Unit
) {
    var name by remember { mutableStateOf(persona?.name ?: "") }
    var prompt by remember { mutableStateOf(persona?.prompt ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (persona == null) "Add Persona" else "Edit Persona") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Persona Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = prompt,
                    onValueChange = { prompt = it },
                    label = { Text("System Prompt") },
                    modifier = Modifier.fillMaxWidth().height(150.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newPersona = persona?.copy(name = name, prompt = prompt) ?: PersonaEntity(name = name, prompt = prompt)
                    onSave(newPersona)
                },
                enabled = name.isNotBlank() && prompt.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
