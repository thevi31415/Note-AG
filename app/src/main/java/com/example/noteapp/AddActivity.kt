package com.example.noteapp

import android.content.*
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_add.*
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    private val noteViewModel: NoteViewModel by lazy {
        ViewModelProvider(
            this,
            NoteViewModel.NoteViewModelFactory(this.application)
        )[NoteViewModel::class.java]
    }
    private val speechRecognizer: SpeechRecognizer by lazy {
        SpeechRecognizer.createSpeechRecognizer(
            this
        )
    }
    private val allowPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            it?.let {
                if (it) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    private var CodeColor = "1"
    private var  N: String = "0"
    private var PasswordCode: String = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        edit_title.setTextColor(Color.parseColor("#FFFFFF"))
        edit_description.setTextColor(Color.parseColor("#FFFFFF"))
        var rd = (1..7).random()
        if (rd == 1) {
            setRed()
        } else if (rd == 2) {
            setOrange()
        } else if (rd == 3) {
            setYellow()
        } else if (rd == 4) {
            setGreen()
        } else if (rd == 5) {
            setBlue()
        } else if (rd == 6) {
            setViolet()
        } else {
            setPink()
        }
        setColorNote()
        checkpassword.setOnClickListener {
            if(N=="0"){

                var builder = AlertDialog.Builder(this)
                val inflater = layoutInflater
                val dialog = inflater.inflate(R.layout.eidttext_layout, null)
                val edittext = dialog.findViewById<EditText>(R.id.edit_pass)
                with(builder){
                    setTitle("Nhập mật khẩu để bật tính năng bảo vệ !")
                    setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->
                        if(edittext.text.toString()==""){
                            Toast.makeText(this@AddActivity, "Bạn chưa nhập mật khẩu ! Vui lòng nhập lại !", Toast.LENGTH_SHORT).show()
                        }else{
                            N="1"
                            PasswordCode = N  + edittext.text.toString()
                            checkpassword.setImageResource(R.drawable.greennot)
                            Toast.makeText(this@AddActivity, "Bật chức năng bảo vệ thành công !", Toast.LENGTH_SHORT).show()
                        }

                    }
                    setNegativeButton("CANCEL"){ dialogInterface: DialogInterface, i: Int ->

                    }
                    setView(dialog)
                    show()

                }
            }else{
                N="0"
                PasswordCode = "0"
                Toast.makeText(this, "Đã tắt chức năng bảo vệ !", Toast.LENGTH_SHORT).show()
                checkpassword.setImageResource(R.drawable.rednot)
            }
        }
        btn_savenew.setOnClickListener {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            if (edit_title.text.toString() == "" || edit_title.text.toString() == " " || edit_title.text.toString() == "  ") {
                edit_title.setText(currentDate.toString())
            }
            if (edit_description.text.toString().trim().length < 100) {
                val note = Note(
                    edit_title.text.toString(),
                    edit_description.text.toString().trim(),
                    edit_description.text.toString().trim(),
                    currentDate.toString(),
                    CodeColor,
                    "A",
                    PasswordCode
                )
                noteViewModel.insertNote(note)
            } else {

                val note = Note(
                    edit_title.text.toString(),
                    edit_description.text.toString().trim(),
                    edit_description.text.toString().subSequence(0, 100).toString().trim() + " ...",
                    currentDate.toString(),
                    CodeColor,
                    "A",
                    PasswordCode
                )
                noteViewModel.insertNote(note)
            }

            MotionToast.createColorToast(
                this,
                "Thông báo",
                "Bạn đã thêm thành công !",
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.SHORT_DURATION,
                ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular)
            )
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btn_copy.setOnClickListener {
            var myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData =
                ClipData.newPlainText("simple text", edit_description.text.toString())
            myClipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copy thành công rồi nha !", Toast.LENGTH_SHORT).show()
        }
        btn_share.setOnClickListener {

            val shareBody = edit_description.text.toString()
            val shapeSub = edit_title.text.toString() + " :"
            if(shareBody==""){
                Toast.makeText(this, "Bạn phải nhập nội dung !", Toast.LENGTH_SHORT).show()
            }else{
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shapeSub)
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(shareIntent)
            }

        }

        btn_voice.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    btn_voice.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.blue));
                    speechRecognizer.stopListening()

                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_DOWN -> {
                    getPermissionOverO(this) {
                        btn_voice.backgroundTintList =
                            ColorStateList.valueOf(resources.getColor(R.color.green));
                        startListen()

                    }

                    Toast.makeText(this, "Nhấn giữ biểu tượng để ghi âm !", Toast.LENGTH_SHORT)
                        .show()

                    return@setOnTouchListener true
                }
                else -> {
                    return@setOnTouchListener true
                }
            }
        }

    }

    private fun setColorNote() {
        dotred.setOnClickListener {
            setRed()

        }
        dotgreen.setOnClickListener {
            setGreen()

        }
        dotongrane.setOnClickListener {
            setOrange()

        }
        dotyellow.setOnClickListener {
            setYellow()

        }
        dotblue.setOnClickListener {
            setBlue()

        }
        dotviolet.setOnClickListener {
            setViolet()

        }
        dotpink.setOnClickListener {
            setPink()

        }
    }

    private fun setRed() {
        dotred.setImageResource(R.drawable.dotredcheck)
        dotongrane.setImageResource(R.drawable.dotorange)
        dotyellow.setImageResource(R.drawable.dotyellow)
        dotgreen.setImageResource(R.drawable.dotgreen)
        dotblue.setImageResource(R.drawable.dotblue)
        dotviolet.setImageResource(R.drawable.dotviolet)
        dotpink.setImageResource(R.drawable.dotpink)
        LaytouAdd.setBackgroundColor(Color.parseColor("#F16055"))
        edit_title.setBackgroundColor(Color.parseColor("#F1847B"))
        edit_description.setBackgroundColor(Color.parseColor("#F1847B"))
        btn_voice.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F16055"))
        btn_share.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F16055"))
        btn_savenew.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F16055"))
        btn_copy.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F16055"))
        window.statusBarColor = resources.getColor(R.color.reddot);
        CodeColor = "1"
    }

    private fun setOrange() {
        dotred.setImageResource(R.drawable.dotred)
        dotongrane.setImageResource(R.drawable.dotorangecheck)
        dotyellow.setImageResource(R.drawable.dotyellow)
        dotgreen.setImageResource(R.drawable.dotgreen)
        dotblue.setImageResource(R.drawable.dotblue)
        dotviolet.setImageResource(R.drawable.dotviolet)
        dotpink.setImageResource(R.drawable.dotpink)
        LaytouAdd.setBackgroundColor(Color.parseColor("#fad7a1"))
        edit_title.setBackgroundColor(Color.parseColor("#F4DAB2"))
        edit_description.setBackgroundColor(Color.parseColor("#F4DAB2"))
        btn_voice.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fad7a1"))
        btn_share.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fad7a1"))
        btn_savenew.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fad7a1"))
        btn_copy.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fad7a1"))
        CodeColor = "2"
        window.statusBarColor = resources.getColor(R.color.organedot);
    }

    private fun setYellow() {
        dotred.setImageResource(R.drawable.dotred)
        dotongrane.setImageResource(R.drawable.dotorange)
        dotyellow.setImageResource(R.drawable.dotyellowcheck)
        dotgreen.setImageResource(R.drawable.dotgreen)
        dotblue.setImageResource(R.drawable.dotblue)
        dotviolet.setImageResource(R.drawable.dotviolet)
        dotpink.setImageResource(R.drawable.dotpink)
        LaytouAdd.setBackgroundColor(Color.parseColor("#F1E264"))
        edit_title.setBackgroundColor(Color.parseColor("#F1E687"))
        edit_description.setBackgroundColor(Color.parseColor("#F1E687"))
        btn_voice.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F1E264"))
        btn_share.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F1E264"))
        btn_savenew.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F1E264"))
        btn_copy.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F1E264"))
        CodeColor = "3"
        window.statusBarColor = resources.getColor(R.color.yellowdot);
    }

    private fun setGreen() {
        dotred.setImageResource(R.drawable.dotred)
        dotongrane.setImageResource(R.drawable.dotorange)
        dotyellow.setImageResource(R.drawable.dotyellow)
        dotgreen.setImageResource(R.drawable.dotgreencheck)
        dotblue.setImageResource(R.drawable.dotblue)
        dotviolet.setImageResource(R.drawable.dotviolet)
        dotpink.setImageResource(R.drawable.dotpink)
        LaytouAdd.setBackgroundColor(Color.parseColor("#59ED5F"))
        edit_title.setBackgroundColor(Color.parseColor("#8CEF90"))
        edit_description.setBackgroundColor(Color.parseColor("#8CEF90"))
        btn_voice.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#59ED5F"))
        btn_share.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#59ED5F"))
        btn_savenew.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#59ED5F"))
        btn_copy.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#59ED5F"))
        CodeColor = "4"
        window.statusBarColor = resources.getColor(R.color.greendot);
    }

    private fun setBlue() {
        dotred.setImageResource(R.drawable.dotred)
        dotongrane.setImageResource(R.drawable.dotorange)
        dotyellow.setImageResource(R.drawable.dotyellow)
        dotgreen.setImageResource(R.drawable.dotgreen)
        dotblue.setImageResource(R.drawable.dotbluecheck)
        dotviolet.setImageResource(R.drawable.dotviolet)
        dotpink.setImageResource(R.drawable.dotpink)
        LaytouAdd.setBackgroundColor(Color.parseColor("#5CBDEA"))
        edit_title.setBackgroundColor(Color.parseColor("#8ACEED"))
        edit_description.setBackgroundColor(Color.parseColor("#8ACEED"))
        btn_voice.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#5CBDEA"))
        btn_share.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#5CBDEA"))
        btn_savenew.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#5CBDEA"))
        btn_copy.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#5CBDEA"))
        CodeColor = "5"
        window.statusBarColor = resources.getColor(R.color.bluedot);
    }

    private fun setViolet() {
        dotred.setImageResource(R.drawable.dotred)
        dotongrane.setImageResource(R.drawable.dotorange)
        dotyellow.setImageResource(R.drawable.dotyellow)
        dotgreen.setImageResource(R.drawable.dotgreen)
        dotblue.setImageResource(R.drawable.dotblue)
        dotviolet.setImageResource(R.drawable.dotviolet_check)
        dotpink.setImageResource(R.drawable.dotpink)
        LaytouAdd.setBackgroundColor(Color.parseColor("#A878F6"))
        edit_title.setBackgroundColor(Color.parseColor("#BD9DF4"))
        edit_description.setBackgroundColor(Color.parseColor("#BD9DF4"))
        btn_voice.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#A878F6"))
        btn_share.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#A878F6"))
        btn_savenew.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#A878F6"))
        btn_copy.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#A878F6"))
        CodeColor = "6"
        window.statusBarColor = resources.getColor(R.color.violetdot);
    }

    private fun setPink() {
        dotred.setImageResource(R.drawable.dotred)
        dotongrane.setImageResource(R.drawable.dotorange)
        dotyellow.setImageResource(R.drawable.dotyellow)
        dotgreen.setImageResource(R.drawable.dotgreen)
        dotblue.setImageResource(R.drawable.dotblue)
        dotviolet.setImageResource(R.drawable.dotviolet)
        dotpink.setImageResource(R.drawable.dotpinkcheck)
        LaytouAdd.setBackgroundColor(Color.parseColor("#F37DED"))
        edit_title.setBackgroundColor(Color.parseColor("#F4A9F1"))
        edit_description.setBackgroundColor(Color.parseColor("#F4A9F1"))
        btn_voice.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F37DED"))
        btn_share.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F37DED"))
        btn_savenew.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F37DED"))
        btn_copy.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F37DED"))
        CodeColor = "7"
        window.statusBarColor = resources.getColor(R.color.pinkdot);
    }

    private fun startListen() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(

            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {

            }

            override fun onBeginningOfSpeech() {
                btn_voice.backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.green));
            }

            override fun onRmsChanged(p0: Float) {

            }

            override fun onBufferReceived(p0: ByteArray?) {

            }

            override fun onEndOfSpeech() {
                btn_voice.backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.blue));
            }

            override fun onError(p0: Int) {

            }

            override fun onResults(bundle: Bundle?) {
                bundle?.let {

                    val result = it.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    val stringb = edit_description.text.toString()
                    edit_description.setText(stringb + result?.get(0))
                }
            }

            override fun onPartialResults(p0: Bundle?) {

            }

            override fun onEvent(p0: Int, p1: Bundle?) {

            }
        })
        speechRecognizer.startListening(intent)
    }

    private fun getPermissionOverO(context: Context, call: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                call.invoke()
            } else {
                allowPermission.launch(android.Manifest.permission.RECORD_AUDIO)
            }
        }

    }

}