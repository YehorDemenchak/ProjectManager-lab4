package com.example.projectmanager.data.repository

import com.example.projectmanager.data.local.ProjectDao
import com.example.projectmanager.data.local.ProjectEntity

class ProjectRepository(
    private val projectDao: ProjectDao
) {
    val projects = projectDao.getAllProjects()

    fun getProjectById(id: Int) = projectDao.getProjectById(id)

    suspend fun addProject(project: ProjectEntity) {
        projectDao.insertProject(project)
    }

    suspend fun updateProject(project: ProjectEntity) {
        projectDao.updateProject(project)
    }

    suspend fun deleteProject(project: ProjectEntity) {
        projectDao.deleteProject(project)
    }
}
