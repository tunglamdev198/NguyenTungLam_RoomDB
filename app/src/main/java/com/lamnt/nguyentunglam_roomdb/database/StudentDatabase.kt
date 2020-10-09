package com.lamnt.nguyentunglam_roomdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lamnt.nguyentunglam_roomdb.database.dao.StudentDAO
import com.lamnt.nguyentunglam_roomdb.model.Student

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao() : StudentDAO
    companion object{
        private var instance : StudentDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context) : StudentDatabase{
            if (instance == null){
                instance = initDb(context)
            }
            return instance as StudentDatabase
        }

        private fun initDb(context: Context): StudentDatabase? {
            return Room.databaseBuilder(context,StudentDatabase::class.java,"StudentDatabase")
                .allowMainThreadQueries()
                .setJournalMode(JournalMode.TRUNCATE)
                .build()
        }
    }


}