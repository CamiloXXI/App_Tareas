package com.example.tareas.Conexion

import androidx.room.*

@Dao
interface TareaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTarea(tarea: Tarea)

    @Query("SELECT * FROM tareas")
    suspend fun obtenerTodasLasTareas(): List<Tarea>

    @Query("SELECT * FROM tareas WHERE id = :id")
    suspend fun obtenerTareaPorId(id: Int): Tarea?

    @Update
    suspend fun actualizarTarea(tarea: Tarea)

    @Query("DELETE FROM tareas WHERE id = :id")
    suspend fun eliminarTareaPorId(id: Int)
}
