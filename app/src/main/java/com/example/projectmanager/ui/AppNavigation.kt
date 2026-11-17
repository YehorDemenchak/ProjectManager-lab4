package com.example.projectmanager.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectmanager.data.local.ProjectEntity
import com.example.projectmanager.ui.screens.AddProjectScreen
import com.example.projectmanager.ui.screens.DetailsProjectScreen
import com.example.projectmanager.ui.screens.ProjectsListScreen
import com.example.projectmanager.ui.viewmodel.ProjectViewModel

@Composable
fun AppNavigation(viewModel: ProjectViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "projects") {

        composable("projects") {
            val projects by viewModel.projects.collectAsState()
            ProjectsListScreen(
                projects = projects,
                onProjectClick = { id -> navController.navigate("details/$id") },
                onAddClick = { navController.navigate("add") }
            )
        }

        composable("add") {
            AddProjectScreen(
                onCreate = { name, description ->
                    viewModel.addProject(ProjectEntity(name = name, description = description))
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            route = "details/{projectId}",
            arguments = listOf(navArgument("projectId") { type = NavType.IntType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getInt("projectId") ?: 0
            val project by remember(projectId) { viewModel.getProjectById(projectId) }
                .collectAsState(initial = null)

            DetailsProjectScreen(
                project = project,
                onProgressChange = { progress ->
                    project?.let { viewModel.updateProject(it.copy(progress = progress)) }
                },
                onDelete = {
                    project?.let { viewModel.deleteProject(it) }
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun ScreenHeader(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2196F3))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}
