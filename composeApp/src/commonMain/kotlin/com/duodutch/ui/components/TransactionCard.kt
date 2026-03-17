package com.duodutch.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duodutch.theme.BackgroundDark
import com.duodutch.theme.DuoDutchTheme

import com.duodutch.theme.SurfaceDark
import com.duodutch.theme.TextPrimaryDark
import com.duodutch.theme.TextSecondaryDark
import com.duodutch.theme.GreenIncome
import com.duodutch.theme.RedExpense
import com.duodutch.utils.toCurrencyString

data class TransacaoMock(
    val titulo: String,
    val data: String,
    val valor: Double,
    val isEntrada: Boolean
)

@Composable
fun TransactionCard(
    transacao: TransacaoMock,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "efeitoMolaCartao"
    )

    val valorFormatado = if (transacao.isEntrada) "+ R$ ${transacao.valor.toCurrencyString()}" else "- R$ ${transacao.valor.toCurrencyString()}"
    val corDoValor = if (transacao.isEntrada) GreenIncome else RedExpense

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceDark)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = transacao.titulo,
                color = TextPrimaryDark,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = transacao.data,
                color = TextSecondaryDark,
                fontSize = 14.sp
            )
        }

        Text(
            text = valorFormatado,
            color = corDoValor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun TransactionCardPreview() {
    DuoDutchTheme {
        Column(
            modifier = Modifier
                .background(BackgroundDark)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp) // Espaço entre os cartões
        ) {
            TransactionCard(
                transacao = TransacaoMock(
                    titulo = "Supermercado",
                    data = "16 de Março",
                    valor = 245.50,
                    isEntrada = false
                ),
                onClick = { /* Não faz nada no preview */ }
            )

            // Testando o cenário de Receita (Verde)
            TransactionCard(
                transacao = TransacaoMock(
                    titulo = "Transferência da Maria",
                    data = "15 de Março",
                    valor = 150.00,
                    isEntrada = true
                ),
                onClick = { /* Não faz nada no preview */ }
            )
        }
    }
}