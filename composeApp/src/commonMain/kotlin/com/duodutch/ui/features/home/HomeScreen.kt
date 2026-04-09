package com.duodutch.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.duodutch.di.AppContainer
import com.duodutch.theme.*
import com.duodutch.ui.components.TransactionCard
import com.duodutch.utils.toCurrencyString

@Composable
fun HomeScreen(appContainer: AppContainer) {
    // INJEÇÃO MANUAL: Criamos o ViewModel usando as peças da nossa fábrica
    val viewModel: HomeViewModel = viewModel(
        factory = viewModelFactory {
            initializer { // <- ESTA É A PALAVRA MÁGICA QUE FALTAVA
                HomeViewModel(
                    transactionRepository = appContainer.transactionRepository,
                    calculateJointBalanceUseCase = appContainer.calculateJointBalanceUseCase
                )
            }
        }
    )

    val state by viewModel.uiState.collectAsState()
    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(BackgroundDark),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                Column(
                    modifier = Modifier.fillMaxWidth().background(SurfaceDark)
                        .padding(top = 40.dp + topPadding, start = 20.dp, end = 20.dp, bottom = 80.dp)
                ) {
                    Text("Welcome", color = TextSecondaryDark, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("DuoDutch", color = TextPrimaryDark, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(48.dp))
                }

                Box(modifier = Modifier.padding(horizontal = 20.dp).offset(y = 40.dp)) {
                    Column(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp))
                            .background(OutlineDark).padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Joint Balance", color = TextSecondaryDark, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        // EXIBE O SALDO REAL CALCULADO
                        Text(
                            text = "R$ ${state.totalBalance.toCurrencyString()}",
                            color = TextPrimaryDark, fontSize = 40.sp, fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(60.dp)) }

        item {
            Text(
                text = "Recent Transactions", color = TextPrimaryDark, fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }

        // EXIBE A LISTA REAL VINDA DO BANCO
        items(state.transactions) { transacao ->
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                TransactionCard(transaction = transacao, onClick = { })
            }
        }
    }
}