import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.tugas13.Note
import com.example.tugas13.R

class NoteAdapter(
    private val context: Context,
    private val notes: List<Note>
) : BaseAdapter() {

    override fun getCount(): Int = notes.size

    override fun getItem(position: Int): Any = notes[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_pemilu, parent, false)

        val note = getItem(position) as Note

        val tvNama = view.findViewById<TextView>(R.id.tvNamaPemilih)
        val tvNIK = view.findViewById<TextView>(R.id.tvNIK)
        val tvGender = view.findViewById<TextView>(R.id.tvGender)
        val tvAlamat = view.findViewById<TextView>(R.id.tvAlamat)

        tvNama.text = note.nama
        tvNIK.text = note.nik
        tvGender.text = note.gender
        tvAlamat.text = note.alamat

        return view
    }
}
