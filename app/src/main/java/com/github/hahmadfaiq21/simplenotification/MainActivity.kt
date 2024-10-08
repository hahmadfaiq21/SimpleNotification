package com.github.hahmadfaiq21.simplenotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.github.hahmadfaiq21.simplenotification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notification permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        val title = getString(R.string.notification_title)
        val message = getString(R.string.notification_message)
        val subtext = getString(R.string.notification_subtext)

        binding.btnSendNotification.setOnClickListener {
            sendNotification(title, message, subtext)
        }

        binding.btnOpenDetail.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_TITLE, title)
            intent.putExtra(DetailActivity.EXTRA_MESSAGE, message)
            startActivity(intent)
        }
    }

    private fun sendNotification(title: String, message: String, subtext: String) {
        val notificationDetailIntent = Intent(this, DetailActivity::class.java)
        notificationDetailIntent.putExtra(DetailActivity.EXTRA_TITLE, title)
        notificationDetailIntent.putExtra(DetailActivity.EXTRA_MESSAGE, message)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(notificationDetailIntent)
            getPendingIntent(
                NOTIFICATION_ID,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setSubText(subtext)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "notification channel"
    }
}