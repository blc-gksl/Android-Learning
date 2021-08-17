package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.button)
        rollButton.setOnClickListener {
            // Show a toast message that the dice is rolled, button pressed
            val toast = Toast.makeText(this, "Dices Rolled!", Toast.LENGTH_SHORT).show()
            //val resultTextView: TextView = findViewById(R.id.textView)
            //resultTextView.text = "6"
            rollDice()
        }
    }
    /**
     * Roll the dice and update the screen with the result.
     */
    private fun rollDice() {
        // Create a Dice object with 6 sides and roll it
        val dice1 = Dice(6)
        val dice1Roll = dice1.roll()

        // Update the screen with the second dice roll
        val resultTextView1: TextView = findViewById(R.id.dice1)
        resultTextView1.text = "Your first dice with size ${dice1.getNumSides()} rolled to ${dice1Roll.toString()}."

        // Find the ImageView in the layout
        val diceImage: ImageView = findViewById(R.id.diceImage)
        //////Put the correct dice picture on the screen
        //Determine which drawable resource ID to use based on the dice roll
        val drawableResource = when (dice1Roll){
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        // Update the ImageView with the correct drawable resource ID
        diceImage.setImageResource(drawableResource)
        // Add information on the content to know which number dice rolled
        diceImage.contentDescription = dice1Roll.toString()
    }
}

/*
 *Dice with a fixed number of sides
 */
class Dice(private val numSides: Int) {
    /*
     *Do a random dice roll and return the result.
     */
    fun roll(): Int {
        return (1..numSides).random()
    }
    /*
     *Get the private variable numSides' value using this function
     */
    fun getNumSides():Int{
        return numSides
    }
}