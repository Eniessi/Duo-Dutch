package com.duodutch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.sqlite.driver.AndroidSQLiteDriver // <-- IMPORTANTE: O Driver Nativo!
import com.duodutch.data.local.getRoomDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val builder = getDatabaseBuilder(applicationContext)

        // Injetamos o motor do próprio Android
        val database = getRoomDatabase(builder, AndroidSQLiteDriver())

        setContent {
            App(database = database)
        }
    }
}