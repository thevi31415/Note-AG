package com.example.noteapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class NoteAdapter(
    private val context: Context,
    private val onClick: (Note) -> Unit,
    private val onDelete: (Note) -> Unit

) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var notes: List<Note> = arrayListOf()

    inner class NoteViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        private val txtTitle: TextView = itemview.findViewById(R.id.text_title)
        private val txtDes: TextView = itemview.findViewById(R.id.text_description)
        private val txttime: TextView = itemview.findViewById(R.id.text_time)
        private val btnDelete: ImageView = itemview.findViewById(R.id.btn_delete)
        private val Layoutitem: CardView = itemview.findViewById(R.id.Layoutitem)
        private val BackgroundItem: LinearLayout=itemview.findViewById(R.id.itemBackground)
        fun onBind(note: Note) {
            txtTitle.text = note.title
            txttime.text = note.timeedit
            if(note.duphong.subSequence(0,1)=="1")
            {
                txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0)
                var Baomat: String = ""
                if(note.description.toString().length > 100){
                    for(i in 1..100){
                        val rand = listOf(('0'..'9'), ('a'..'z'), ('A'..'Z')).flatten().random()
                        Baomat = Baomat + rand.toString()
                    }
                    Baomat=Baomat + " ..."
                }else{
                    for(i in 1..note.description.toString().length){
                        val rand = listOf(('0'..'9'), ('a'..'z'), ('A'..'Z')).flatten().random()
                        Baomat = Baomat + rand.toString()
                    }
                }

                txtDes.text = Baomat
                txtDes.setTextColor(Color.parseColor("#000000"))

            }else{
                txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rong, 0, 0, 0)
                txtDes.text = note.description_text
                txtDes.setTextColor(Color.parseColor("#000000"))
            }
            var ColorCode = note.color
            if (ColorCode == "1") {
                Layoutitem.setCardBackgroundColor(Color.parseColor("#F16055"))
                BackgroundItem.setBackgroundResource(R.drawable.redgradient)
            } else if (ColorCode == "2") {
                Layoutitem.setCardBackgroundColor(Color.parseColor("#F1B966"))
                BackgroundItem.setBackgroundResource(R.drawable.orgnegrandient)
            } else if (ColorCode == "3") {
                Layoutitem.setCardBackgroundColor(Color.parseColor("#F1E264"))
                BackgroundItem.setBackgroundResource(R.drawable.yellowgrandient)
            } else if (ColorCode == "4") {
                Layoutitem.setCardBackgroundColor(Color.parseColor("#59ED5F"))
                BackgroundItem.setBackgroundResource(R.drawable.greengradient)

            } else if (ColorCode == "5") {
                Layoutitem.setCardBackgroundColor(Color.parseColor("#5CBDEA"))
                BackgroundItem.setBackgroundResource(R.drawable.bluegradient)

            } else if (ColorCode == "6") {
                Layoutitem.setCardBackgroundColor(Color.parseColor("#A87BF6"))
                BackgroundItem.setBackgroundResource(R.drawable.violetgradient)
            } else {
                Layoutitem.setCardBackgroundColor(Color.parseColor("#F37DED"))
                BackgroundItem.setBackgroundResource(R.drawable.pinkgrandenit)
            }
            btnDelete.setOnClickListener { onDelete(note) }
            Layoutitem.setOnClickListener { onClick(note) }
            /*   Layoutitem.setOnLongClickListener {

                 return@setOnLongClickListener =true
               }*/

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemview =
            LayoutInflater.from(context).inflate(R.layout.activity_item_view, parent, false)
        return NoteViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun randomColor(): String {
        var red: String = "#f05d5d"
        var blue: String = "#55e8f2"
        var green: String = "#51f56f"
        var yellow: String = "#f5f251"
        var pink: String = "#f26bce"
        var purple: String = "#d58cf5"
        val id = Random.nextInt(0, 6)
        if (id == 0) {
            return red;
        } else if (id == 1) {
            return blue
        } else if (id == 2) {
            return green
        } else if (id == 3) {
            return yellow
        } else if (id == 4) {
            return pink
        } else {
            return purple
        }
    }
}