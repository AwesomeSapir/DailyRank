package com.sapreme.dailyrank.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sapreme.dailyrank.data.model.Group
import com.sapreme.dailyrank.data.repository.GroupRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GroupsViewModel(
    private val repo: GroupRepository
): ViewModel() {

    private val currentUserId: String = FirebaseAuth.getInstance().uid!!

    val groups: StateFlow<List<Group>> =  repo.getMyGroups(currentUserId).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun createGroup(name: String) = viewModelScope.launch {
        repo.createGroup(name, currentUserId)
    }

    fun joinGroup(groupId: String) = viewModelScope.launch {
        repo.joinGroup(groupId, currentUserId)
    }

}