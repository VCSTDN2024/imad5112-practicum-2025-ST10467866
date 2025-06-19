package com.example.musicplaylist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detailedView : AppCompatActivity() {

    private lateinit var songs: ArrayList<String>
    private lateinit var artists: ArrayList<String>
    private lateinit var ratings: ArrayList<Int>
    private lateinit var genres: ArrayList<String>
    private lateinit var displayTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_deatailed_view)


        songs = intent.getStringArrayListExtra("song") ?: arrayListOf()
        artists = intent.getStringArrayListExtra("artist") ?: arrayListOf()
        ratings = intent.getIntegerArrayListExtra("rating") ?: arrayListOf()
        genres = intent.getStringArrayListExtra("genre") ?: arrayListOf()
        displayTextView = findViewById(R.id.displaytextView)

        val viewPlaylistButton: Button = findViewById(R.id.viewbtn)
        val averageRatingButton: Button = findViewById(R.id.avgbtn)
        val returnButton: Button = findViewById(R.id.returnbtn)

        viewPlaylistButton.setOnClickListener {
            displayPlaylistDetails()
        }

        averageRatingButton.setOnClickListener {
            displayAverageRatingWithDetails()
        }

        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun displayPlaylistDetails() {
        val stringBuilder = StringBuilder()
        if (songs.isNotEmpty()) {
            stringBuilder.append("--- Full Playlist Details ---\n\n")
            for (i in songs.indices) {
                val songName = songs.getOrNull(i) ?: "N/A"
                val artistName = artists.getOrNull(i) ?: "N/A"
                val songRating = ratings.getOrNull(i)?.toString() ?: "N/A"
                val songGenre = genres.getOrNull(i) ?: "N/A"

                stringBuilder.append("Song: $songName\n")
                stringBuilder.append("Artist: $artistName\n")
                stringBuilder.append("Rating: $songRating/5\n")
                stringBuilder.append("Genre: $songGenre\n\n")
            }
            displayTextView.text = stringBuilder.toString()
        } else {
            displayTextView.text = "Playlist is empty. No details to display."
        }
    }

    private fun displayAverageRatingWithDetails() {
        if (ratings.isNotEmpty()) {
            var sumOfRatings = 0
            for (r in ratings) {
                sumOfRatings += r
            }
            val average = sumOfRatings.toDouble() / ratings.size
            val formattedAverage = String.format("%.1f", average)

            val stringBuilder = StringBuilder()
            stringBuilder.append("--- Playlist Song Ratings ---\n\n")

            for (i in songs.indices) {
                val songName = songs.getOrNull(i) ?: "N/A"
                val artistName = artists.getOrNull(i) ?: "N/A"
                val songRating = ratings.getOrNull(i)?.toString() ?: "N/A"

                stringBuilder.append("Song: $songName\n")
                stringBuilder.append("Artist: $artistName\n")
                stringBuilder.append("Rating: $songRating/5\n\n")
            }

            stringBuilder.append("-----------------------------------\n")
            stringBuilder.append("Average Playlist Rating: $formattedAverage/5\n")
            stringBuilder.append("-----------------------------------\n")

            displayTextView.text = stringBuilder.toString()
        } else {
            displayTextView.text = "Playlist is empty. No ratings available to calculate an average."
        }
    }
}