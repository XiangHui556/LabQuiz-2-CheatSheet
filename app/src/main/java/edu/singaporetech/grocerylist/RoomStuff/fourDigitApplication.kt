package edu.singaporetech.madata
import android.app.Application

class FourDigitApplication  : Application()
{
    private val database by lazy { FourDigitRoomDatabase.getDatabase(this) }
    val repository by lazy { FourDigitRepository(database.fourDigitDao()) }
}