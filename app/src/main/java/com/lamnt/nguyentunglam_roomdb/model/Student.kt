package com.lamnt.nguyentunglam_roomdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "student")
data class Student(
    @PrimaryKey
    @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "gender") var gender: Boolean,
    @ColumnInfo(name = "math") var math: Float,
    @ColumnInfo(name = "physical") var physical: Float,
    @ColumnInfo(name = "chemistry") var chemistry: Float
) : Serializable