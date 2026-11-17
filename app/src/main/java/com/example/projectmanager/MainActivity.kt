package com.example.projectmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectmanager.data.local.DatabaseProvider
import com.example.projectmanager.data.repository.ProjectRepository
import com.example.projectmanager.ui.AppNavigation
import com.example.projectmanager.ui.viewmodel.ProjectViewModel
import com.example.projectmanager.ui.viewmodel.ProjectViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val database = DatabaseProvider.getDatabase(context)
            val repository = ProjectRepository(database.projectDao())

            val viewModel: ProjectViewModel = viewModel(
                factory = ProjectViewModelFactory(repository)
            )

            MaterialTheme {
                AppNavigation(viewModel)
            }
        }
    }
}
