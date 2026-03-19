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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.duodutch.domain.models.Transaction
import com.duodutch.domain.models.TransactionType

import com.duodutch.theme.SurfaceDark
import com.duodutch.theme.TextPrimaryDark
import com.duodutch.theme.TextSecondaryDark
import com.duodutch.theme.GreenIncome
import com.duodutch.theme.RedExpense
import com.duodutch.utils.toCurrencyString

@Composable
fun TransactionCard(
    transaction: Transaction, // Recebendo o domínio puro
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

    // Usando o Enum Type-Safe em vez de Booleanos
    val isIncome = transaction.type == TransactionType.INCOME

    val formattedAmount = if (isIncome) {
        "+ $ ${transaction.amount.toCurrencyString()}"
    } else {
        "- $ ${transaction.amount.toCurrencyString()}"
    }

    val amountColor = if (isIncome) GreenIncome else RedExpense

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
                text = transaction.title,
                color = TextPrimaryDark,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = transaction.date,
                color = TextSecondaryDark,
                fontSize = 14.sp
            )
        }

        Text(
            text = formattedAmount,
            color = amountColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}