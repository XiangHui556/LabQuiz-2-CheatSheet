package edu.singaporetech.grocerylist

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class PrimeWorker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {
    private val TAG: String = "PrimeWorker"

    override fun doWork(): Result {
        val num: Long = inputData.getString("NUMBER")?.toLong() ?: return Result.failure()
        val result = isPrime(num)
        val outputData = workDataOf("is_prime" to result)
        return Result.success(outputData)
    }

    private fun isPrime(num: Long): Boolean{
        for (i in 2..(num / 2)){
            if (num % i == 0L){
                Log.d(TAG, "Not Prime")
                return false
            }
        }
        Log.d(TAG, "Prime")
        return true
    }
}