package com.example.noteapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.text.SimpleDateFormat
import androidx.appcompat.widget.SearchView
import java.util.*
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.custom_toast.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val noteViewModel: NoteViewModel by lazy {
        ViewModelProvider(
            this,
            NoteViewModel.NoteViewModelFactory(this.application)
        )[NoteViewModel::class.java]
    }
    private val adapter: NoteAdapter by lazy {
        NoteAdapter(this@MainActivity, onItemClick, onItemDelete)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Recycler_view1.startLayoutAnimation()

        initControls()
        initEvent()
        onResume()
        search_view?.isSubmitButtonEnabled = true
        search_view?.setOnQueryTextListener(this)
    }

    override fun onResume() {
        super.onResume()

        var N: Int
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        var editor = preferences.edit()
        N = preferences.getInt("firstt", 0)
        if (N == 0) {
            var rd = (1..7).random()
            var ColorString = ""
            if (rd == 1) {
                ColorString = "1"
            } else if (rd == 2) {
                ColorString = "2"
            } else if (rd == 3) {
                ColorString = "3"
            } else if (rd == 4) {
                ColorString = "4"
            } else if (rd == 5) {
                ColorString = "5"
            } else if (rd == 6) {
                ColorString = "6"
            } else {
                ColorString = "7"
            }
            val layout = layoutInflater.inflate(R.layout.custom_toast, ll_wrapper)
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            val note = Note(
                "Xin chào bạn !",
                "Cảm ơn bạn đã sử dụng app của chúng tôi ! \nnoteag31415@gmail.com",
                "Cảm ơn bạn đã sử dụng app của chúng tôi ! \nnoteag31415@gmail.com",
                currentDate.toString(),
                ColorString,
                "A",
                "0"
            )
            noteViewModel.insertNote(note)
            Toast(this).apply {
                duration = Toast.LENGTH_LONG
                setGravity(Gravity.CENTER, 0, 0)
                view = layout
            }.show()
            editor.putInt("firstt", 1)
            editor.apply()
            // Recycler_view1.startLayoutAnimation()
        }
    }

    /*   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
           menuInflater.inflate(R.menu.menu, menu)
           return true
       }

       override fun onOptionsItemSelected(item: MenuItem): Boolean {
           when (item.itemId) {
               R.id.menu_calender -> {
                   //startActivity(Intent(this, Calender::class.java))
               }
               R.id.menu_feedback -> {
                   startActivity(Intent(this, Feedback::class.java))
               }
               R.id.menu_info -> {
                   startActivity(Intent(this, Info::class.java)) }//startActivity(Intent(this@MainActivity, InfoActivity::class.java))
               R.id.menu_exit -> {
                   builder = AlertDialog.Builder(this)
                   builder.setTitle("Thông báo ")
                       .setMessage("Bạn có muốn thoát khỏi ứng dụng không ?")
                       .setCancelable(true)
                       .setPositiveButton("No") { dialogInterface, it ->
                           dialogInterface.cancel()
                       }
                       .setPositiveButton("Yes") { dialogInterface, it ->
                           moveTaskToBack(true);exitProcess(-1)
                       }.show()
               }
           }
           return super.onOptionsItemSelected(item)
       }*/

    private fun initEvent() {

        btn_add.setOnClickListener {


            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)

        }
        btn_send1.setOnClickListener {
            startActivity(Intent(this@MainActivity, Feedback::class.java))
        }
    }

    private fun initControls() {
        Recycler_view1.setHasFixedSize(true)
        Recycler_view1.layoutManager = LinearLayoutManager(this)
        Recycler_view1.adapter = adapter
        noteViewModel.getAllNote().observe(this, Observer {
            adapter.setNotes(it)
        })

    }

    private val onItemClick: (Note) -> Unit = {
            if(it.duphong.subSequence(0, 1)=="1")
            {
                var builder = AlertDialog.Builder(this)
                val inflater = layoutInflater
                val dialog = inflater.inflate(R.layout.eidttext_click, null)
                val edittext = dialog.findViewById<EditText>(R.id.edit_pass)
                with(builder){
                    setTitle("Ghi chú này được bảo vệ phải nhập mật khẩu để mở !")
                    setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->
                       if(edittext.text.toString() == it.duphong.subSequence(1, it.duphong.length)){
                           val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                           intent.putExtra("UPDATE_NOTE", it)
                           startActivity(intent)
                       }else{
                           Toast.makeText(this@MainActivity, "Bạn đã nhập sai, vui lòng nhập lại !", Toast.LENGTH_SHORT).show()
                       }
                    }
                    setNegativeButton("CANCEL"){ dialogInterface: DialogInterface, i: Int ->

                    }
                    setView(dialog)
                    show()

                }
            }else{

                val intent = Intent(this, UpdateActivity::class.java)
                intent.putExtra("UPDATE_NOTE", it)
                startActivity(intent)
            }




    }
    private val onItemDelete: (Note) -> Unit = {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cảnh báo !")
        builder.setMessage("Bạn có chắc là muốn xóa ghi chú không ?")
        builder.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int ->
            noteViewModel.deleteNote(it)
            Recycler_view1.startLayoutAnimation()
            MotionToast.createColorToast(
                this,
                "Thông báo",
                "Đã xóa thành công !",
                MotionToastStyle.WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.SHORT_DURATION,
                ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular)
            )
        })
        builder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int ->

        })
        builder.show()

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query == null || query == " " || query == "") {
            val query = "A"
            searchDatabaseAll(query)

            // Recycler_view1.startLayoutAnimation()
        } else {
            searchDatabase(query)
            // Recycler_view1.startLayoutAnimation()
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query == null || query == " " || query == "") {
            val query = "A"
            searchDatabaseAll(query)
            Recycler_view1.startLayoutAnimation()
        } else {
            searchDatabase(query)
            Recycler_view1.startLayoutAnimation()
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        noteViewModel.searchDatabase(searchQuery).observe(
            this
        ) { list ->
            list.let {
                adapter.setNotes(it)
            }
        }
    }

    private fun searchDatabaseAll(query: String) {
        val searchQuery = "%$query%"
        noteViewModel.searchDatabaseAll(searchQuery).observe(
            this
        ) { list ->
            list.let {
                adapter.setNotes(it)
            }
        }
    }
}

