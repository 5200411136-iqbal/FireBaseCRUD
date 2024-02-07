package com.example.firebasecrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasecrud.databinding.ActivityListMahasiswaBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListMahasiswa : AppCompatActivity() {
    private lateinit var binding:ActivityListMahasiswaBinding
    private var firebaseDatabase:FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.getReference("data")
        getdata()
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this@ListMahasiswa, MainActivity::class.java))
        }
    }
    private fun getdata() {
        databaseReference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (data in snapshot.children){
                    val id = data.key
                    val nama = data.child("nama").value.toString()
                    val npm = data.child("npm").value.toString()
                    val user = User(id = id,nama = nama, npm = npm)
                    list.add(user)
                }
                binding.recyclerView.setHasFixedSize(true)
                binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                val adp = AdapterMahasiswa(list)
                adp.notifyDataSetChanged()
                binding.recyclerView.adapter = adp
                adp.setOnItemClick(object : AdapterMahasiswa.onAdapterListener{
                    override fun onClick(list: User) {
                        val intent = Intent(this@ListMahasiswa, MainActivity::class.java)
                        intent.putExtra("id", list.id)
                        intent.putExtra("nama", list.nama)
                        intent.putExtra("npm", list.npm.toString())
                        startActivity(intent)
                    }
                    override fun onClickDel(list: User) {
                       val selectedId = list.id.toString()
                        databaseReference?.child(selectedId)?.removeValue()
                    }
                })
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("ERR","onCancelled: ${error.toException()}")
            }
        })
    }
}