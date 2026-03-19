package com.duodutch.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duodutch.domain.models.Transaction
import com.duodutch.domain.models.TransactionType

import com.duodutch.theme.BackgroundDark
import com.duodutch.theme.OutlineDark
import com.duodutch.theme.SurfaceDark
import com.duodutch.theme.TextPrimaryDark
import com.duodutch.theme.TextSecondaryDark
import com.duodutch.ui.components.TransactionCard

@Composable
fun HomeScreen() {
    val mockTransactions = listOf(
        Transaction("1", "Supermarket", "Today", 245.50, TransactionType.EXPENSE),
        Transaction("2", "Maria's Transfer", "Yesterday", 150.00, TransactionType.INCOME),
        Transaction("3", "Netflix", "March 14", 55.90, TransactionType.EXPENSE),
        Transaction("4", "Restaurant", "March 12", 180.00, TransactionType.EXPENSE),
        Transaction("5", "Gas Station", "March 10", 200.00, TransactionType.EXPENSE),
        Transaction("6", "Pharmacy", "March 08", 85.00, TransactionType.EXPENSE),
        Transaction("7", "Salary", "March 05", 5000.00, TransactionType.INCOME)
    )

    // Lemos a altura da barra do relógio (Status Bar) para empurrar o texto para baixo
    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark), // Fundo base da tela
        contentPadding = PaddingValues(bottom = 100.dp) // Espaço para não grudar no fundo
    ) {

        // ITEM 1: O Cabeçalho Bicolor + Cartão Sobreposto
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Fundo claro do topo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceDark)
                        // APLICANDO A MÁGICA DO RELÓGIO AQUI:
                        .padding(top = 40.dp + topPadding, start = 20.dp, end = 20.dp, bottom = 80.dp)
                ) {
                    Text("Welcome", color = TextSecondaryDark, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("DuoDutch", color = TextPrimaryDark, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(48.dp))
                }

                // Cartão Flutuante de Saldo
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .offset(y = 40.dp) // Puxa o cartão 40 pixels para baixo
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(OutlineDark)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Joint Balance", color = TextSecondaryDark, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "R$ 4.250,00",
                            color = TextPrimaryDark,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // ITEM 2: Espaçador
        item { Spacer(modifier = Modifier.height(60.dp)) }

        // ITEM 3: Título da Lista
        item {
            Text(
                text = "Recent Transactions",
                color = TextPrimaryDark,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }

        // ITEM 4: A Lista de Cartões
        items(mockTransactions) { transacao ->
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                TransactionCard(
                    transaction = transacao,
                    onClick = { /* Abre detalhes */ }
                )
            }
        }
    }
}