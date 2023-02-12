package com.alfikri.rizky.circlefeed

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alfikri.rizky.circlefeed.databinding.ActivityMainBinding
import com.alfikri.rizky.core.constant.DeeplinkConstant
import com.alfikri.rizky.core.navigation.DeeplinkHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var deeplinkHandler: DeeplinkHandler

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            handleIntent(it)
        }

        viewModel.state.observe(this) { state ->
            when (state) {
                is MainSplashState.ShowMainSplashLabel -> showSplashLabel()

                is MainSplashState.ShowMainSplashButton -> showSplashButton()

                is MainSplashState.NavigateToHomeScreen -> navigateToHomeScreen()
            }
        }
    }

    private fun handleIntent(intent: Intent) {
        intent.data?.toString()?.let {
            deeplinkHandler.process(it)
            finish()
        }
    }

    private fun showSplashLabel() {
        ObjectAnimator.ofFloat(binding.tvLabel, View.ALPHA, 1f).apply {
            duration = 500
            start()
        }
    }

    private fun showSplashButton() {
        ObjectAnimator.ofFloat(binding.btnSplash, View.ALPHA, 1f).apply {
            duration = 500
            start()
        }

        binding.btnSplash.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(DeeplinkConstant.AUTHENTICATION_DEEPLINK)
            )
            startActivity(intent)
            finish()
        }
    }

    private fun navigateToHomeScreen() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(DeeplinkConstant.STORY_DEEPLINK)
        )
        startActivity(intent)
        finish()
    }
}