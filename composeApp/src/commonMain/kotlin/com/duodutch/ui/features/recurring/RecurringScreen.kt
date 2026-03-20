package com.duodutch.ui.features.recurring

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duodutch.domain.models.RecurringBill
import com.duodutch.domain.usecases.GetDaysUntilDueUseCase

import com.duodutch.theme.BackgroundDark
import com.duodutch.theme.OrangePrimary
import com.duodutch.theme.OutlineDark
import com.duodutch.theme.SurfaceDark
import com.duodutch.theme.TextPrimaryDark
import com.duodutch.theme.TextSecondaryDark
import com.duodutch.utils.toCurrencyString
@Composable
fun RecurringScreen() {
    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val getDaysUntilDueUseCase = remember { GetDaysUntilDueUseCase() }

    val upcomingBills = listOf(
        RecurringBill("1", "Spotify Duo", 27.90, 15),
        RecurringBill("2", "Electricity", 145.50, 20),
        RecurringBill("3", "Fiber Internet", 99.90, 25),
        RecurringBill("4", "Apartment Rent", 1500.00, 5)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {

        // ITEM 1: Split Background + Overlapping Calendar
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Top Layer (SurfaceDark) - Now structurally protected
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceDark)
                        .padding(top = topPadding + 20.dp)
                ) {
                    Text(
                        text = "Recurring",
                        color = TextPrimaryDark,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Tabs: Upcoming / All
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Upcoming", color = OrangePrimary, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(modifier = Modifier.width(120.dp).height(3.dp).background(OrangePrimary))
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("All", color = TextSecondaryDark, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(modifier = Modifier.width(120.dp).height(3.dp).background(BackgroundDark))
                        }
                    }

                    // TECH LEAD FIX: A barreira estrutural.
                    // Em vez de usar padding que esmaga o texto, nós forçamos a coluna a ter
                    // um espaço vazio no final que é exatamente do tamanho que o calendário precisa
                    // para flutuar sem cobrir as abas.
                    Spacer(modifier = Modifier.height(140.dp))
                }

                // Overlapping Calendar Card
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .offset(y = 40.dp) // O deslocamento matemático que faz ele vazar para a cor de baixo
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(OutlineDark)
                            .padding(20.dp)
                    ) {
                        Text("Coming Up", color = TextPrimaryDark, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("You have 1 recurring charge within the next 7 days.", color = TextSecondaryDark, fontSize = 14.sp)

                        Spacer(modifier = Modifier.height(24.dp))

                        // Calendar Mock
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val weekDays = listOf("S", "M", "T", "W", "T", "F", "S")
                            val monthDays = listOf("14", "15", "16", "17", "18", "19", "20")

                            weekDays.forEachIndexed { index, day ->
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(day, color = TextSecondaryDark, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(12.dp))

                                    if (monthDays[index] == "17") { // Highlighted Day
                                        Box(
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clip(CircleShape)
                                                .background(OrangePrimary),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(monthDays[index], color = BackgroundDark, fontWeight = FontWeight.Bold)
                                        }
                                    } else {
                                        Box(modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center) {
                                            Text(monthDays[index], color = TextPrimaryDark)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // ITEM 2: Spacer to compensate for the calendar offset
        item { Spacer(modifier = Modifier.height(60.dp)) }

        // ITEM 3: List Title
        item {
            Text(
                text = "UPCOMING BILLS",
                color = TextSecondaryDark,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
            )
        }

        // ITEM 4: Bills List
        items(upcomingBills) { bill ->
            val daysUntilDue = getDaysUntilDueUseCase(bill)

            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                RecurringBillCard(bill, daysUntilDue)
            }
        }
    }
}

@Composable
fun RecurringBillCard(bill: RecurringBill, daysLeft: Int) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceDark)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(OutlineDark),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = TextSecondaryDark)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = bill.name, // Usando a propriedade real
                color = TextPrimaryDark,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "due in $daysLeft days", // Lógica separada do dado
                color = TextSecondaryDark,
                fontSize = 14.sp
            )
        }

        Text(
            text = "$ ${bill.amount.toCurrencyString()}",
            color = TextPrimaryDark,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(8.dp))
        Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = TextSecondaryDark)
    }
}