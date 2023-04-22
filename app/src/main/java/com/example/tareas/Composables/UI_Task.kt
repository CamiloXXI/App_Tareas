package com.example.tareas.Composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tareas.Conexion.AppDatabase
import com.example.tareas.Conexion.Tarea
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaTareas(tareas: List<Tarea>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de tareas") }
            )
        }
    ) {
        LazyColumn {
            items(tareas) { tarea ->
                TareaItem(tarea = tarea)
            }
        }
    }
}

@Composable
fun TareaItem(tarea: Tarea) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = tarea.nombre, style = MaterialTheme.typography.bodyMedium)
            Text(text = tarea.descripcion, style = MaterialTheme.typography.bodySmall)
            Checkbox(
                checked = tarea.completado,
                onCheckedChange = { /* hacer algo cuando se cambia el estado de la tarea */ },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(database: AppDatabase) {
    val coroutineScope = rememberCoroutineScope()
    val listaTareas by produceState<List<Tarea>>(emptyList()) {
        coroutineScope.launch {
            val tareas = database.tareaDao().obtenerTodasLasTareas()
            value = tareas
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Tareas") })
        }
    ) {
        // Composable que muestra la lista de tareas
        ListaTareas(listaTareas)
    }
}




@Preview
@Composable
fun PreviewPantallaPrincipal() {
    val context = LocalContext.current
    val database = AppDatabase.getInstance(context)
    val appDatabase = database

    PantallaPrincipal(appDatabase)



}
