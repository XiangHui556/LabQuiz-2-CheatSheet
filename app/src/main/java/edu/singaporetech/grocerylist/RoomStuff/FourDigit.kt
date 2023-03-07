package edu.singaporetech.madata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.Job

@Entity(tableName = "four_digit_item_table")
class FourDigit(
    @ColumnInfo(name = "number") var number: Int = 0,
    @PrimaryKey val id: Int
)