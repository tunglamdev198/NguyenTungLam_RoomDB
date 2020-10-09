package com.lamnt.nguyentunglam_roomdb.database.dao

import androidx.room.*
import com.lamnt.nguyentunglam_roomdb.model.Student
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface StudentDAO {

    @Query("Select * from student")
    fun getListStudent() : Observable<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStudent(student: Student) : Completable

    @Delete
    fun deleteStudent(student: Student) : Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateStudent(student: Student) : Completable
}