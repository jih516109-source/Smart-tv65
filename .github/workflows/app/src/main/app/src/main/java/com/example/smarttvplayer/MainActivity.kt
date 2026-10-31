package com.example.smarttvplayer

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerView = findViewById(R.id.playerView)
        setupPlayer()
    }

    private fun setupPlayer() {
        player = ExoPlayer.Builder(this).build()
        playerView.player = player
    }

    fun playStream(url: String) {
        if (url.isNotEmpty()) {
            val mediaItem = MediaItem.fromUri(Uri.parse(url))
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        } else {
            Toast.makeText(this, "Please enter a valid Stream URL", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                if (!player.isPlaying) player.play() else player.pause()
                return true
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                player.seekTo(player.currentPosition - 10000)
                return true
            }
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                player.seekTo(player.currentPosition + 10000)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}
