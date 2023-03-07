package edu.singaporetech.madata
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface FourDigitDao {
    @Query("SELECT * FROM four_digit_item_table ORDER BY id ASC")
    fun allDigits(): Flow<List<FourDigit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDigitItem(digitItem: FourDigit)

    @Update
    suspend fun updateDigitItem(digitItem: FourDigit)

    @Query("DELETE FROM four_digit_item_table")
    suspend fun dropTable()
}