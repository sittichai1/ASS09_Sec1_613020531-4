package com.example.lab9mysqlupdatedelete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_edit_delete.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback

class EditDeleteActivity : AppCompatActivity() {
    val createClient : StudentAPI = StudentAPI.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_delete)

        val mId =intent.getStringExtra("mId")
        val mName =intent.getStringExtra("mName")
        val mAge =intent.getStringExtra("mAge")

        edt_id.setText(mId)
        edt_id.isEnabled =false
        edt_name.setText(mName)
        edt_age.setText(mAge)
    }

    fun saveStudent(v: View) {
        createClient.updateStudent(
            edt_id.text.toString(),
            edt_name.text.toString(),
            edt_age.text.toString().toInt()).enqueue(object : Callback<Student> {
            override fun onResponse(call: Call<Student>, response: Response<Student>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Successfully Updated", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Error ", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Student>, t: Throwable) {
                Toast.makeText(applicationContext, "Error onFailure " + t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
    fun deleteStudent(v: View){
        val myBuilder = AlertDialog.Builder(this)
        myBuilder.apply {
            setTitle("Warning")
            setMessage("Do you want to delete the Student ?")
            setNegativeButton("Yes"){
                dialog,which->
                createClient.deleteStudent(edt_id.text.toString())
                    .enqueue(object : Callback<Student>{
                        override fun onResponse(call: Call<Student>, response: Response<Student>) {
                            Toast.makeText(applicationContext,"Successfully Deleted", Toast.LENGTH_LONG).show()
                        }

                        override fun onFailure(call: Call<Student>, t: Throwable) {
                            Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                        }
                    })
                finish()
            }
            setPositiveButton("No"){dialog, which->dialog.cancel()}
            show()
        }
    }

}
