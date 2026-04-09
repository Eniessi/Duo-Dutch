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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.duodutch.di.AppContainer
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
fun RecurringScreen(
    appContainer: AppContainer
) {

    val viewModel: RecurringViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                RecurringViewModel(
                    getDaysUntilDueUseCase = appContainer.getDaysUntilDueUseCase
                )
            }
        }
    )

    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val state by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {

        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceDark)
                        .padding(top = topPadding + 20.dp)
                ) {
                    Text(
                        text = "Recurring", color = TextPrimaryDark, fontSize = 20.sp,
                        fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
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
                    Spacer(modifier = Modifier.height(140.dp))
                }

                Box(
                    modifier = Modifier.padding(horizontal = 20.dp).offset(y = 40.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp))
                            .background(OutlineDark).padding(20.dp)
                    ) {
                        Text("Coming Up", color = TextPrimaryDark, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("You have charges within the next 7 days.", color = TextSecondaryDark, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(24.dp))

                        // 3. O CALENDÁRIO BURRO: Apenas desenha o que está no estado (state)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            state.weekDaysInitials.forEachIndexed { index, dayInitial ->
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(dayInitial, color = TextSecondaryDark, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(12.dp))

                                    val isToday = index == state.todayIndex

                                    if (isToday) {
                                        Box(
                                            modifier = Modifier.size(32.dp).clip(CircleShape).background(OrangePrimary),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(state.weekDaysNumbers[index], color = BackgroundDark, fontWeight = FontWeight.Bold)
                                        }
                                    } else {
                                        Box(modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center) {
                                            Text(state.weekDaysNumbers[index], color = TextPrimaryDark)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(60.dp)) }

        item {
            Text(
                text = "UPCOMING BILLS", color = TextSecondaryDark, fontSize = 12.sp,
                fontWeight = FontWeight.Bold, letterSpacing = 1.sp, modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
            )
        }

        // 4. A LISTA BURRA: Lê as contas e dias restantes do estado (state)
        items(state.upcomingBillsWithDaysLeft) { (bill, daysLeft) ->
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                RecurringBillCard(bill = bill, daysLeft = daysLeft)
            }
        }
    }
}

@Composable
fun RecurringBillCard(bill: RecurringBill, daysLeft: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp))
            .background(SurfaceDark).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(48.dp).clip(CircleShape).background(OutlineDark),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = TextSecondaryDark)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = bill.name, color = TextPrimaryDark, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "due in $daysLeft days", color = TextSecondaryDark, fontSize = 14.sp)
        }
        Text(
            text = "$ ${bill.amount.toCurrencyString()}", color = TextPrimaryDark,
            fontSize = 16.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = TextSecondaryDark)
    }
}