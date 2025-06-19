package com.example.musicplaylist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

// Welcome to the Music Playlist App
// Student number: ST10467866
// Name & Surname: Leah Naiker

class MainActivity : AppCompatActivity() {


    private val songs = mutableListOf<String>()
    private val artists = mutableListOf<String>()
    private val ratings = mutableListOf<Int>() // Corrected type
    private val genres = mutableListOf<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createBtn = findViewById<Button>(R.id.createbtn)
        val detailedBtn = findViewById<Button>(R.id.detailedbtn)
        val exitButton = findViewById<Button>(R.id.Exitbtn)
        val imageView = findViewById<ImageView>(R.id.imageView)

        //Create button used to add songs to the playlist
        createBtn.setOnClickListener {
            showAddPlaylistDialog()
        }

        //Detailed view button used to view the playlist
        detailedBtn.setOnClickListener {
            if (songs.isNotEmpty()) {
                val intent = Intent(this, detailedView::class.java)
                intent.putStringArrayListExtra("song", ArrayList(songs))
                intent.putStringArrayListExtra("artist", ArrayList(artists))
                intent.putIntegerArrayListExtra("rating", ArrayList(ratings))
                intent.putStringArrayListExtra("genre", ArrayList(genres))
                startActivity(intent)
            } else {
                Snackbar.make(
                    detailedBtn,
                    "Playlist is empty. Add items first.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        //Exit button used to exit the app completely
        exitButton.setOnClickListener {
            finishAffinity()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showAddPlaylistDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Song")

        val view = layoutInflater.inflate(R.layout.dialog_add_playlist, null)
        val songEditText: EditText = view.findViewById(R.id.songtxt)
        val artistEditText: EditText = view.findViewById(R.id.artisttxt)
        val ratingEditText: EditText = view.findViewById(R.id.ratingtxt)
        val genreEditText: EditText = view.findViewById(R.id.genretxt)

        builder.setView(view)

        builder.setPositiveButton("Add") { dialogInterface, _ ->
            val songInput = songEditText.text.toString().trim()
            val artistInput = artistEditText.text.toString().trim()
            val ratingStringInput = ratingEditText.text.toString().trim()
            val genreInput = genreEditText.text.toString().trim()

            if (songInput.isEmpty() || artistInput.isEmpty() || ratingStringInput.isEmpty() || genreInput.isEmpty()) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Song name, artist, rating, and genre cannot be empty.",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setPositiveButton
            }

            val ratingInputInt = ratingStringInput.toIntOrNull()
            // Validate rating is between 1 and 5 (inclusive)
            if (ratingInputInt == null || ratingInputInt !in 1..5) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Invalid rating. Please enter a rating between 1 and 5.",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setPositiveButton
            }

            // Add to the class member lists
            this.songs.add(songInput)
        }
    }
    }
