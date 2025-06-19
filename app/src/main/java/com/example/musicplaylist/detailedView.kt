package com.example.musicplaylist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.system.exitProcess

class detailedView : AppCompatActivity() {

    private lateinit var songs: ArrayList<String>
    private lateinit var artists: ArrayList<String>
    private lateinit var rating: ArrayList<Int>
    private lateinit var genre: ArrayList<String>
    private lateinit var displayTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_deatailed_view)


        songs = intent.getStringArrayListExtra("item") ?: arrayListOf()
        artists = intent.getStringArrayListExtra("category") ?: arrayListOf()
        rating = intent.getIntegerArrayListExtra("quantity") ?: arrayListOf()
        genre = intent.getStringArrayListExtra("comments") ?: arrayListOf()
        displayTextView = findViewById(R.id.displaytextView)

        val ViewButton: Button = findViewById(R.id.viewbtn)
        val AverageRatingButton: Button = findViewById(R.id.avgbtn)
        val returnButton: Button = findViewById(R.id.returnbtn)

        ViewButton.setOnClickListener {
            displayDetailedView()
        }

        AverageRatingButton.setOnClickListener {
            displayAverageRating()
        }

        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun displayDetailedView() {
        val stringBuilder = StringBuilder()
        if (songs.isNotEmpty()) {
            for (i in songs.indices) {
                stringBuilder.append("Item: ${songs[i]}\n")
                stringBuilder.append("Category: ${artists[i]}\n")
                stringBuilder.append("Quantity: ${rating[i]}\n")
                stringBuilder.append("Comments: ${genre[i]}\n\n")
            }
            displayTextView.text = stringBuilder.toString()
        } else {
            displayTextView.text = "Playlist is empty."
        }
    }
    private fun displayAverageRating() {
        if (rating.isNotEmpty()) {
            var sumOfRatings = 0
            for (r in rating) {
                sumOfRatings += r
            }
            // Calculate average
            val average = if (rating.isEmpty()) 0.0 else sumOfRatings.toDouble() / rating.size

            // Format the average to one or two decimal places for better readability
            val formattedAverage = String.format("%.1f", average)

            val stringBuilder = StringBuilder()
            stringBuilder.append("--- Playlist Ratings ---\n\n")

            for (i in songs.indices) {
                if (i < artists.size && i < rating.size) {
                    stringBuilder.append("Song: ${songs[i]}\n")
                    stringBuilder.append("Artist: ${artists[i]}\n")
                    stringBuilder.append("Rating: ${rating[i]}/5\n\n")
                }
            }
            stringBuilder.append("--------------------------\n")
            stringBuilder.append("Average Playlist Rating: $formattedAverage/5\n")

            displayTextView.text = stringBuilder.toString()
        } else {
            displayTextView.text = "Playlist is empty. No ratings to calculate average."
        }
    }
}

