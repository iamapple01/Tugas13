package com.example.tugas13

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tugas13.Note
import com.example.tugas13.NoteDao
import com.example.tugas13.NoteRoomDatabase
import com.example.tugas13.R
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class EntryActivity : AppCompatActivity() {
    private lateinit var mNotesDao: NoteDao
    private lateinit var executorService: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        executorService = Executors.newSingleThreadExecutor()
        val db = NoteRoomDatabase.getDatabase(this)
        mNotesDao = db.noteDao()

        val etNamaPemilih = findViewById<EditText>(R.id.etNamaPemilih)
        val etNIK = findViewById<EditText>(R.id.etNIK)
        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        val etAlamat = findViewById<EditText>(R.id.etAlamat)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            val nama = etNamaPemilih.text.toString()
            val nik = etNIK.text.toString()
            val gender = when (radioGroupGender.checkedRadioButtonId) {
                R.id.radiolakilaki -> "Laki-laki"
                R.id.radioperempuan -> "Perempuan"
                else -> ""
            }
            val alamat = etAlamat.text.toString()

            if (nama.isEmpty() || nik.isEmpty() || gender.isEmpty() || alamat.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            executorService.execute {
                val note = Note(
                    nama = nama,
                    nik = nik,
                    gender = gender,
                    alamat = alamat
                )
                mNotesDao.insert(note)
                runOnUiThread {
                    Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
    }
}
