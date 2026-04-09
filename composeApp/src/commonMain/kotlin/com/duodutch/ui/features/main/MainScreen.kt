package com.duodutch.ui.features.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.duodutch.di.AppContainer

import com.duodutch.theme.BackgroundDark
import com.duodutch.theme.OrangePrimary
import com.duodutch.ui.components.BottomBarItem
import com.duodutch.ui.components.DuoDutchBottomBar
import com.duodutch.ui.features.home.HomeScreen
import com.duodutch.ui.features.recurring.RecurringScreen

@Composable
fun MainScreen(appContainer: AppContainer) {
    var currentTab by remember { mutableStateOf(0) }

    val tabs = listOf(
        BottomBarItem(icon = Icons.Default.Home, label = "Dashboard"),
        BottomBarItem(icon = Icons.Default.Refresh, label = "Recurring")
    )

    Scaffold(
        containerColor = BackgroundDark, // A cor base absoluta
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Nova transação */ },
                containerColor = OrangePrimary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New Transaction",
                    tint = BackgroundDark
                )
            }
        },
        bottomBar = {
            DuoDutchBottomBar(
                currentTab = currentTab,
                onTabSelected = { currentTab = it },
                items = tabs
            )
        }
    ) { paddingValues ->
        // Este Box é a "janela" por onde vemos a HomeScreen ou a RecurringScreen.
        // O padding bottom impede que a BottomBar tampe a nossa lista.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            when (currentTab) {
                0 -> HomeScreen(appContainer = appContainer)
                1 -> RecurringScreen(appContainer = appContainer)
            }
        }
    }
}