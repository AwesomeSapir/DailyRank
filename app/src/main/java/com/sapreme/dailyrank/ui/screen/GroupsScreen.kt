package com.sapreme.dailyrank.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapreme.dailyrank.data.model.Group

@Composable
fun GroupsScreen() {
    val groups = remember { mutableStateListOf<Group>() }

    GroupsScreenContent(
        groups = groups
    ) { }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreenContent(
    groups: List<Group>,
    onGroupSelected: (String) -> Unit
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    var showJoinDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Groups") })
        },
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
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(groups) { group ->
                        GroupItem(group, onClick = { onGroupSelected(group.id) })
                    }
                }
            }

            if (showCreateDialog) {

            }

            if (showJoinDialog) {

            }
        }
    }
}

@Composable
private fun GroupItem(
    group: Group,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        ListItem(
            headlineContent = { Text(group.name) },
            supportingContent = { Text("Members: ${group.members.size}") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GroupsScreenPreview() {
    val sampleGroups = listOf(
        Group(id = "1", name = "Alpha Squad", createdBy = "user1", members = listOf("user1", "user2")),
        Group(id = "2", name = "Beta Team", createdBy = "user2", members = listOf("user2"))
    )
    GroupsScreenContent(
        groups = sampleGroups,
        onGroupSelected = {}
    )
}
