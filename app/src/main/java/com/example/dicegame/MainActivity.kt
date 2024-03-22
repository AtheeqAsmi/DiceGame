package com.example.dicegame

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import android.widget.Toast
//https://drive.google.com/drive/folders/1cPs2V-SOlrgSLrYW4EtomWRVjzWCe8Ym?usp=share_link
//the above link is the video presentation of the coursework

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val about = findViewById<Button>(R.id.button2)

        about.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("20210708-Atheeq Asmi")
            builder.setMessage("I confirm that I understand what plagiarism is and have read and understood the section on Assessment Offences in the Essential Information for Students. The work that I have submitted is entirely my own. Any work from other authors is duly referenced and acknowledged.\n")
            builder.setPositiveButton("OK") { dialog, _ ->
                // this does nothing, it just dismisses the dialog
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
        val targetEditText = findViewById<EditText>(R.id.target_score_input)

        val newGame = findViewById<Button>(R.id.button1)

        newGame.setOnClickListener {
            val targetScore = if (targetEditText.text.isNotBlank()) targetEditText.text.toString().toInt() else 101
            val intent = Intent(this, NewGame::class.java).apply {
                putExtra("TARGET_SCORE", targetScore)
            }
            startActivity(intent)


        }


    }


    }