package com.sapreme.dailyrank.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapreme.dailyrank.data.model.Group
import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.data.remote.firebase.FirebaseAuthManager
import com.sapreme.dailyrank.data.repository.GroupRepository
import com.sapreme.dailyrank.data.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val auth: FirebaseAuthManager,
    private val groupRepo: GroupRepository,
    private val playerRepo: PlayerRepository,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val groupsFlow: Flow<List<Group>> =
        auth.authState.filterNotNull()
            .flatMapLatest { groupRepo.observeGroups(it.uid) }

    val groups: StateFlow<List<Group>> =
        groupsFlow
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    @OptIn(ExperimentalCoroutinesApi::class)
    val playersByGroup: StateFlow<Map<String, List<Player>>> =
        groupsFlow.flatMapLatest { groups ->
            if (groups.isEmpty()) flowOf(emptyMap())
            else combine(
                groups.map { group -> playerRepo.observePlayersInGroup(group.id) }
            ) { playerLists ->
                buildMap {
                    groups.forEachIndexed { index, group ->
                        put(group.id, playerLists[index])
                    }
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyMap()
        )


}