package edu.singaporetech.madata

import androidx.annotation.WorkerThread
import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.flow.Flow

class FourDigitRepository(private val fourDigitDao: FourDigitDao)
{
    val allDigits: Flow<List<FourDigit>> = fourDigitDao.allDigits()

    @WorkerThread
    suspend fun insertDigits(digitItem: FourDigit)
    {
        fourDigitDao.insertDigitItem(digitItem)
    }

    @WorkerThread
    suspend fun dropTable()
    {
        fourDigitDao.dropTable()
    }
}