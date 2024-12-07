package com.example.tugas13


import NoteAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas13.databinding.ActivityMainBinding
import com.example.tugas13.LoginActivity
import com.example.tugas13.PrefManager


class MainActivity : AppCompatActivity() {
    private lateinit var mNotesDao: NoteDao
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager

    companion object {
        const val REQUEST_CODE_ENTRY = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager.getInstance(this)
        checkLoginStatus()

        with(binding) {
            btnLogout.setOnClickListener {
                prefManager.setLoggedIn(false)
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
            btnTambahData.setOnClickListener {
                val intent = Intent(this@MainActivity, EntryActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_ENTRY)
            }
        }
    }

    private fun checkLoginStatus() {
        if (!prefManager.isLoggedIn()) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }

        val db = NoteRoomDatabase.getDatabase(this)
        mNotesDao = db.noteDao()

        // Observasi data untuk ListView
        mNotesDao.getAllNotes().observe(this) { notes ->
            val adapter = NoteAdapter(this, notes)
            binding.listView.adapter = adapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ENTRY && resultCode == RESULT_OK) {
            mNotesDao.getAllNotes().observe(this) { notes ->
                val adapter = NoteAdapter(this, notes)
                binding.listView.adapter = adapter
            }
        }
    }
}
