package com.duodutch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Importando nosso ecossistema
import com.duodutch.theme.OrangePrimary
import com.duodutch.theme.SurfaceDark
import com.duodutch.theme.TextSecondaryDark

@Composable
fun DuoDutchBottomBar(
    currentTab: Int,
    onTabSelected: (Int) -> Unit,
    // Passamos a lista de ícones e nomes aqui para deixar o componente flexível
    items: List<BottomBarItem>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark) // Fica na cor do cabeçalho/cartões
            // O padding bottom dá espaço para a "linha" de navegação nativa do iPhone
            .padding(bottom = 24.dp)
            .height(68.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            BottomNavItem(
                icon = item.icon,
                label = item.label,
                isSelected = currentTab == index,
                onClick = { onTabSelected(index) }
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = if (isSelected) OrangePrimary else TextSecondaryDark
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = interactionSource,
                indication = null, // Tira a onda cinza do Android
                onClick = onClick
            )
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // A MÁGICA DA IMAGEM: A linha superior
        Box(
            modifier = Modifier
                .width(48.dp) // Largura da linha
                .height(3.dp) // Espessura fina e elegante
                .background(if (isSelected) OrangePrimary else Color.Transparent)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = color,
            fontSize = 11.sp // Texto bem pequeno e discreto
        )
    }
}

// Uma classe simples para definirmos os botões
data class BottomBarItem(val icon: ImageVector, val label: String)