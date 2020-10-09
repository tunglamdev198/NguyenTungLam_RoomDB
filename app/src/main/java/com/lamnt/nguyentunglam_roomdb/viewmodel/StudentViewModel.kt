package com.lamnt.nguyentunglam_roomdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lamnt.nguyentunglam_roomdb.database.StudentDatabase
import com.lamnt.nguyentunglam_roomdb.model.Student
import io.reactivex.Completable
import io.reactivex.Observable

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private var mApplication : Application = application

    fun getListStudent() : Observable<List<Student>>{
        return StudentDatabase.getDatabase(mApplication.applicationContext)
            .studentDao()
            .getListStudent()
    }

    fun addStudent(student : Student) : Completable{
        return StudentDatabase.getDatabase(mApplication.applicationContext)
            .studentDao()
            .addStudent(student)
    }

    fun updateStudent(student: Student) : Completable{
        return StudentDatabase.getDatabase(mApplication.applicationContext)
            .studentDao()
            .updateStudent(student)
    }

    fun deleteStudent(student: Student) : Completable{
        return StudentDatabase.getDatabase(mApplication.applicationContext)
            .studentDao()
            .deleteStudent(student)
    }
}