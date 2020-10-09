package com.lamnt.nguyentunglam_roomdb.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lamnt.nguyentunglam_roomdb.R
import com.lamnt.nguyentunglam_roomdb.adapter.ListStudentAdapter
import com.lamnt.nguyentunglam_roomdb.model.Student
import com.lamnt.nguyentunglam_roomdb.viewmodel.StudentViewModel
import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list_student.*

class ListStudentActivity : AppCompatActivity(), ListStudentAdapter.OnItemClickListener {

    private lateinit var studentViewModel: StudentViewModel
    private lateinit var mCompositeDisposable: CompositeDisposable
    private var students: ArrayList<Student> = ArrayList()
    private var positionEdit: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_student)
        initDataAndViews()
    }

    @SuppressLint("CheckResult")
    private fun initDataAndViews() {
        val mFactory = ViewModelProvider.AndroidViewModelFactory(application)
        studentViewModel = ViewModelProvider(this, mFactory).get(StudentViewModel::class.java)

        mCompositeDisposable = CompositeDisposable()

        val observer = object : Observer<List<Student>> {
            override fun onSubscribe(d: Disposable) {
                mCompositeDisposable.add(d)
            }

            override fun onNext(t: List<Student>) {
                students.clear()
                students.addAll(t)
                rvStudents.run {
                    adapter = ListStudentAdapter(students, this@ListStudentActivity)
                }
            }

            override fun onError(e: Throwable) {
                Toast.makeText(this@ListStudentActivity, e.message, Toast.LENGTH_SHORT).show()
            }

            override fun onComplete() {

            }
        }

        studentViewModel
            .getListStudent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

        btnAdd.setOnClickListener {
            addStudent()
        }


    }

    private fun addStudent() {
        val intent = Intent(this, AddEditStudentActivity::class.java)
        intent.putExtra("mode", "add")
        startActivity(intent)
    }

    override fun onEdit(student: Student, position: Int) {
        positionEdit = position
        val intent = Intent(this, AddEditStudentActivity::class.java)
        intent.putExtra("mode", "edit")
        intent.putExtra("student", student)
        startActivity(intent)
    }

    override fun onRemove(position: Int) {
        var builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
            .setTitle("Xoá")
            .setMessage("Bạn có chắc chắn muốn xoá?")
            .setPositiveButton("Có") { dialog, _ ->
                deleteStudent(students[position])
                dialog?.dismiss()
            }
            .setNegativeButton(
                "Không"
            ) { dialog, _ -> dialog?.dismiss() }
        builder.create().show()
    }

    private fun deleteStudent(student: Student) {
        studentViewModel.deleteStudent(student)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    mCompositeDisposable.add(d)
                }

                override fun onComplete() {
                    Toast.makeText(this@ListStudentActivity,"Xoá thành công!",Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@ListStudentActivity,e.message,Toast.LENGTH_SHORT).show()
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }
}