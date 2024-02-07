package com.example.firebasecrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasecrud.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    private var firebaseDatabase : FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    private var userid:String? = null
    private var nama:String? = null
    private var npm:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.getReference("data")

        userid = intent.getStringExtra("id")
        Log.e("USER_ID = ",userid.toString())

        if (userid == null || userid == ""){
            Toast.makeText(this@MainActivity, "Halaman Tambah Data", Toast.LENGTH_SHORT).show()
        }else{

            nama = intent.getStringExtra("nama")
            npm = intent.getStringExtra("npm")
            binding.edtNama.setText(nama)
            binding.edtNpm.setText(npm)

            Toast.makeText(this@MainActivity, "Halaman Edit Data", Toast.LENGTH_SHORT).show()
        }


        binding.btnSave.setOnClickListener {
            if(userid == null){
                val getRandomId = UUID.randomUUID()
                var uid = getRandomId.toString()
                val nama = binding.edtNama.text.toString()
                val npm = binding.edtNpm.text.toString()
                val user = User(nama = nama, npm = npm)
                databaseReference?.child(uid)?.setValue(user)
                Toast.makeText(this, "Berhasil tambah Data", Toast.LENGTH_SHORT).show()
            }else{
                val nama = binding.edtNama.text.toString()
                val npm = binding.edtNpm.text.toString()
                val user = User(nama = nama, npm = npm)
                databaseReference?.child(userid.toString())?.setValue(user)
                Toast.makeText(this@MainActivity, "Berhasil Update Data", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(this@MainActivity, ListMahasiswa::class.java))
        }

    }
}