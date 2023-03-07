package edu.singaporetech.grocerylist

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class RandomService : Service() {
    private val binder = LocalBinder()
    private var job: Job? = null
    private var randomList = ArrayList<Long> ()

    val randomNumber: Long
        get() = randomList[randomList.size - 1]

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        job = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                // Generate random 9-digit number
                val randomLong = Random.nextLong(999999999 - 100000000) + 100000000
                randomList.add(randomLong)
                // Print out the generated number
                Log.d(TAG, "$randomLong added")
                delay(1000)
            }
        }
        return START_STICKY
    }

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): RandomService = this@RandomService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }

    companion object {
        private const val TAG: String = "RandomService"
    }
}