package edu.singaporetech.grocerylist

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import edu.singaporetech.grocerylist.databinding.ActivityServiceBinding

/**
 * CSC2007 Lab 05
 *
 * Author: your teacher
 * Updated: the day before your lab.
 *
 */
class ServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceBinding // View binding
    private lateinit var mService: RandomService

    // Flag to indicate if service is bounded
    private var mBound: Boolean = false

    private val TAG: String = "ServiceActivity"

    // Defines callbacks for service binding, passed to bindService()
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to RandomService, cast the IBinder and get LocalService instance
            val binder = service as RandomService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }



    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Service
        binding.buttonCurrentRandom.setOnClickListener {
            if (mBound) {
                // Call a method from the LocalService.
                // However, if this call were something that might hang, then this request should
                // occur in a separate thread to avoid slowing down the activity performance.
                val num: Long = mService.randomNumber
                binding.textEditPrime.setText(num.toString())
            }
        }

        // WorkManager
        binding.buttonCheckPrime.setOnClickListener {
            val num = binding.textEditPrime.text.toString()

            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()

            val isPrimeWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<PrimeWorker>()
                .setInputData(workDataOf(
                    "NUMBER" to num
                ))
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(this).enqueue(isPrimeWorkRequest)
            val startTime = System.currentTimeMillis()

            WorkManager.getInstance(this).getWorkInfoByIdLiveData(isPrimeWorkRequest.id).observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED){
                    val wasPrime = workInfo.outputData.getBoolean("is_prime", false)
                    val runtime = System.currentTimeMillis() - startTime
                    if (wasPrime){
                        binding.textViewResult.text = getString(R.string.prime_result, num, getString(R.string.is_prime), runtime)
                    }
                    else{
                        binding.textViewResult.text = getString(R.string.prime_result, num, getString(R.string.not_prime), runtime)
                    }
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        // Bind to RandomService
        val serviceIntent = Intent(this, RandomService::class.java)
        startService(serviceIntent)
        bindService(Intent(this, RandomService::class.java), connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (mBound){
            unbindService(connection)
            mBound = false
        }
    }

}