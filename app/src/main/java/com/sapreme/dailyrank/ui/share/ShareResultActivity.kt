package com.sapreme.dailyrank.ui.share

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sapreme.dailyrank.ui.screen.ShareResultScreen
import com.sapreme.dailyrank.ui.viewmodel.GameResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareResultActivity: ComponentActivity() {

    private val viewModel: GameResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let { raw ->
                viewModel.parse(raw)
            }
        }

        setContent {
            ShareResultScreen(viewModel = viewModel)
        }
    }
}