package com.duodutch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.duodutch.data.local.getRoomDatabase // Importante!

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // 1. O Android usa o Context para descobrir onde salvar o SQLite
        val builder = getDatabaseBuilder(applicationContext)
        // 2. Criamos a Base de Dados
        val database = getRoomDatabase(builder)

        setContent {
            // 3. Entregamos a base de dados à aplicação (Fim da tela branca!)
            App(database = database)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}