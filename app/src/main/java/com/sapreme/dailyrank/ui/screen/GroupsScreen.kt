package com.sapreme.dailyrank.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sapreme.dailyrank.data.model.Group
import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.ui.component.CreateGroupDialog
import com.sapreme.dailyrank.ui.component.GroupCard
import com.sapreme.dailyrank.ui.theme.Spacing
import com.sapreme.dailyrank.viewmodel.GroupsViewModel

@Composable
fun GroupsScreen(
    viewModel: GroupsViewModel = hiltViewModel()
) {
    val groups by viewModel.groups.collectAsState()
    val playersByGroup by viewModel.playersByGroup.collectAsState()

    GroupsScreenContent(
        groups = groups,
        playersByGroup = playersByGroup,
        onGroupSelected = {},
        onGroupCreate = { name -> viewModel.createGroup(name) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreenContent(
    groups: List<Group>,
    playersByGroup: Map<String, List<Player>>,
    onGroupSelected: (String) -> Unit,
    onGroupCreate: (String) -> Unit,
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    var showJoinDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(onClick = { showCreateDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Create Group")
                }
                FloatingActionButton(onClick = { showJoinDialog = true }) {
                    Icon(Icons.Default.GroupAdd, contentDescription = "Join Group")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (groups.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("You have no groups. Create or join one!")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(Spacing.l)
                ) {
                    items(groups) { group ->
                        val playerList = playersByGroup[group.id] ?: emptyList()
                        GroupCard(
                            group = group,
                            playerList = playerList,
                            onClick = { onGroupSelected(group.id) }
                        )
                    }
                }
            }

            if (showCreateDialog) {
                CreateGroupDialog(
                    onDismiss = { showCreateDialog = false },
                    onCreate = onGroupCreate
                )
            }

            if (showJoinDialog) {
                //TODO
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupsScreenPreview() {
    val sampleGroups = listOf(
        Group(id = "1", name = "Alpha Squad", createdBy = "u1"),
        Group(id = "2", name = "Beta Team", createdBy = "u2"),
    )

    val samplePlayersByGroup = mapOf(
        // ── Alpha Squad members ───────────────────────
        "1" to listOf(
            Player(uid = "u1", nickname = "Alice"),
            Player(uid = "u2", nickname = "Bob"),
            Player(uid = "u3", nickname = "Charlie"),
        ),
        // ── Beta Team members ─────────────────────────
        "2" to listOf(
            Player(uid = "u2", nickname = "Dana"),
            Player(uid = "u1", nickname = "Eli"),
        )
    )

    GroupsScreenContent(
        groups = sampleGroups,
        playersByGroup = samplePlayersByGroup,
        onGroupSelected = {}
    )
}
