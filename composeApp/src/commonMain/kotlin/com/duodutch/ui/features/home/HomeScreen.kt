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

import com.duodutch.theme.BackgroundDark
import com.duodutch.theme.DuoDutchTheme
import com.duodutch.theme.OutlineDark
import com.duodutch.theme.SurfaceDark
import com.duodutch.theme.TextPrimaryDark
import com.duodutch.theme.TextSecondaryDark
import com.duodutch.ui.components.TransacaoMock
import com.duodutch.ui.components.TransactionCard

@Composable
fun HomeScreen() {
    val mockTransactions = listOf(
        TransacaoMock("Supermercado", "Hoje", 245.50, false),
        TransacaoMock("Pix da Maria", "Ontem", 150.00, true),
        TransacaoMock("Netflix", "14 de Março", 55.90, false),
        TransacaoMock("Restaurante", "12 de Março", 180.00, false),
        TransacaoMock("Gasolina", "10 de Março", 200.00, false),
        TransacaoMock("Farmácia", "08 de Março", 85.00, false),
        TransacaoMock("Salário", "05 de Março", 5000.00, true)
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
                    Text("Bem-vindos de volta,", color = TextSecondaryDark, fontSize = 16.sp)
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
                        Text("Saldo do Casal", color = TextSecondaryDark, fontSize = 14.sp)
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
                text = "Transações Recentes",
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
                    transacao = transacao,
                    onClick = { /* Abre detalhes */ }
                )
            }
        }
    }
}