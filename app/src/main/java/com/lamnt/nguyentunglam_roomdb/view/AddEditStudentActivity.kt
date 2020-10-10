package com.lamnt.nguyentunglam_roomdb.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lamnt.nguyentunglam_roomdb.R
import com.lamnt.nguyentunglam_roomdb.model.Student
import com.lamnt.nguyentunglam_roomdb.utils.ValidateUtil
import com.lamnt.nguyentunglam_roomdb.viewmodel.StudentViewModel
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class AddEditStudentActivity : AppCompatActivity() {
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var mode: String
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        val mFactory = ViewModelProvider.AndroidViewModelFactory(application)
        studentViewModel = ViewModelProvider(this, mFactory).get(StudentViewModel::class.java)
        mode = intent?.getStringExtra("mode")!!
        if ("add" == mode) {
            btnAction.text = "Thêm"
        } else {
            btnAction.text = "Sửa"
            val student = intent?.getSerializableExtra("student") as Student
            edtId.setText(student.id)
            edtName.setText(student.name)
            edtMath.setText(student.math.toString())
            edtChemistry.setText(student.chemistry.toString())
            edtPhysical.setText(student.physical.toString())
            if (student.gender) {
                rbMale.isChecked = true
            } else {
                rbFemale.isChecked = true
            }
        }

        actionClick()
    }

    private fun actionClick() {
        if (validateStudent()) {
            btnAction.setOnClickListener {
                val student = Student(
                    edtId.text.toString(),
                    edtName.text.toString(),
                    rbMale.isChecked,
                    edtMath.text.toString().toFloat(),
                    edtPhysical.text.toString().toFloat(),
                    edtChemistry.text.toString().toFloat()
                )
                if (mode == "add") {
                    addStudent(student)
                } else {
                    editStudent(student)
                }
            }
        }
    }

    private fun addStudent(student: Student) {
        studentViewModel
            .addStudent(student)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    Toast.makeText(
                        this@AddEditStudentActivity,
                        "Thêm thành công!",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent()
                    intent.putExtra("student", student)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@AddEditStudentActivity, e.message, Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }

    private fun editStudent(student: Student) {
        studentViewModel
            .updateStudent(student)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    Toast.makeText(
                        this@AddEditStudentActivity,
                        "Sửa thành công!",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent()
                    intent.putExtra("student", student)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }

                override fun onError(e: Throwable) {

                }

            })
    }

    private fun validateStudent(): Boolean {
        if (ValidateUtil.isNullOrEmpty(edtId.text.toString().trim())) {
            Toast.makeText(this, "Vui lòng nhập mã sinh viên!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (ValidateUtil.isNullOrEmpty(edtName.text.toString().trim())) {
            Toast.makeText(this, "Vui lòng nhập tên sinh viên!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (ValidateUtil.isNullOrEmpty(edtMath.text.toString().trim())) {
            Toast.makeText(this, "Vui lòng nhập điểm toán!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (ValidateUtil.isNullOrEmpty(edtPhysical.text.toString().trim())) {
            Toast.makeText(this, "Vui lòng nhập điểm lý!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (ValidateUtil.isNullOrEmpty(edtChemistry.text.toString().trim())) {
            Toast.makeText(this, "Vui lòng nhập điểm hoá!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}