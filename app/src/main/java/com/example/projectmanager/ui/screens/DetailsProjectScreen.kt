package com.example.projectmanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectmanager.data.local.ProjectEntity
import com.example.projectmanager.ui.ScreenHeader
import kotlin.math.roundToInt

@Composable
fun DetailsProjectScreen(
    project: ProjectEntity?,
    onProgressChange: (Float) -> Unit,
    onDelete: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(topBar = { ScreenHeader("Деталі проекту") }) { padding ->
        if (project == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Проект не знайдено")
            }
            return@Scaffold
        }

        // Поточне значення слайдера; в базу зберігається, коли користувач відпускає повзунок
        var sliderValue by remember(project.id) { mutableFloatStateOf(project.progress) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(project.name, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                    Text(project.description, color = Color.Gray)
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Прогрес", color = Color.Gray)
                    Text(
                        "${(sliderValue * 100).roundToInt()}%",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Slider(
                        value = sliderValue,
                        onValueChange = { sliderValue = it },
                        onValueChangeFinished = { onProgressChange(sliderValue) }
                    )
                }
            }

            OutlinedButton(
                onClick = onDelete,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Видалити") }
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Назад") }
        }
    }
}
