package com.example.dicegame

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class NewGame : AppCompatActivity() {

    var tempTotal = 0
    var tempTotal2 = 0
    var compTotalScore = 0
    var playerTotalScore = 0
    var throwButtonCount = 0
    var playerThrowTotal = 0
    var compThrowTotal = 0
    var compWins = 0
    var playerWins = 0
    val selectedDiceList = mutableListOf<ImageView>()
    var isTieBreakerThrow = false
    private lateinit var selectedDice : ImageView
    private lateinit var throwButton : Button
    private lateinit var scoreButton : Button
    private lateinit var playerScoreText :TextView
    private lateinit var compScoreText :TextView
    private lateinit var playerDiceImageView: Array<ImageView>
    private lateinit var compDiceImageView: Array<ImageView>



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        playerScoreText = findViewById<TextView>(R.id.playerScore)
        compScoreText = findViewById<TextView>(R.id.computerScore)

        compDiceImageView = arrayOf(
            findViewById(R.id.computerDice1),
            findViewById(R.id.computerDice2),
            findViewById(R.id.computerDice3),
            findViewById(R.id.computerDice4),
            findViewById(R.id.computerDice5)
        )
        playerDiceImageView = arrayOf(
            findViewById(R.id.playerDice1),
            findViewById(R.id.playerDice2),
            findViewById(R.id.playerDice3),
            findViewById(R.id.playerDice4),
            findViewById(R.id.playerDice5)
        )
        throwButton = findViewById(R.id.throw_button)
        scoreButton = findViewById(R.id.score_button)
        scoreButton.isEnabled=false



    }
    // In NewGameActivity


    fun throwBtnClickListener(view: View) {
        computerDiceThrow()
        playerDiceThrow()
        throwButton.text="Reroll"
        scoreButton.isEnabled=true
        throwButtonCount += 1
        if(throwButtonCount>=1){
            selectReroll()
            selectedDiceList.clear()

        }
        if(throwButtonCount==3) {
            scoreBtnClickListener(view)
        }

    }


    fun scoreBtnClickListener(view: View) {
        var targetScore = intent.getIntExtra("TARGET_SCORE", 101)
        scoreButton()
        throwButton.text="Throw"
        scoreButton.isEnabled=false
        throwButtonCount = 0
        if(!isTieBreakerThrow) {
            if (compTotalScore >= targetScore && playerTotalScore >= targetScore) {
                tieBreaker()
            } else if (compTotalScore >= targetScore) {
                // Computer wins
                showResultDialog(false)
            } else if (playerTotalScore >= targetScore) {
                // Player wins
                showResultDialog(true)
            } else {
                //continue playing
            }
        }
        else{
            if (playerTotalScore>compTotalScore){
                showResultDialog(true)
            }
            else{
                showResultDialog(false)
            }
        }
    }
    private fun tieBreaker(){
        playerTotalScore=0
        compTotalScore=0
        isTieBreakerThrow = true
        playerScoreText.text = "Player Score: $playerTotalScore"
        compScoreText.text = "Computer Score: $compTotalScore"
        AlertDialog.Builder(this)
            .setMessage("This is a tie breaker")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()

            }
            .create()
            .apply {
                show()
            }
        throwButton.isEnabled=true
        var computerThrowTotal = 0
        for (imageView in compDiceImageView) {
            val randomDiceFace = (1..6).random() // generate a random number between 1 and 6
            var diceImage = when (randomDiceFace) {
                1 -> R.drawable.face_one
                2 -> R.drawable.face_two
                3 -> R.drawable.face_three
                4 -> R.drawable.face_four
                5 -> R.drawable.face_five
                else -> R.drawable.face_six
            }
            imageView.setImageResource(diceImage) // set the image resource of the ImageView
            computerThrowTotal += randomDiceFace
            compThrowTotal = computerThrowTotal
        }
        tempTotal2 = computerThrowTotal

        var userThrowTotal = 0
        for (imageView in playerDiceImageView) {
            val randomDiceFace = (1..6).random() // generate a random number between 1 and 6
            var diceImage = when (randomDiceFace) {
                1 -> R.drawable.face_one
                2 -> R.drawable.face_two
                3 -> R.drawable.face_three
                4 -> R.drawable.face_four
                5 -> R.drawable.face_five
                else -> R.drawable.face_six
            }
            // set the image resource of the ImageView if it is not in the selectedDiceList
            if(imageView !in selectedDiceList) {
                imageView.setImageResource(diceImage)
            } else {
                imageView.alpha = 1f
            }
            //get the total for this specific throw
            userThrowTotal += randomDiceFace
            playerThrowTotal = userThrowTotal
        }
        //set the throw total to the playerTotalScore if score is pressed
        tempTotal = userThrowTotal


    }

    private fun showResultDialog(isWin: Boolean) {
        val message = if (isWin) "You won!" else "You lost!"
        val color = if (isWin) R.color.green else R.color.red
        var score = findViewById<TextView>(R.id.points)
        if (isWin){
            playerWins += 1
            score.text = "H:$playerWins / C:$compWins"
        }
        else{
            compWins += 1
            score.text = "H:$playerWins / C:$compWins"
        }

        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                throwButton.isEnabled=false

            }
            .create()
            .apply {
                window?.setBackgroundDrawableResource(color)
                show()
            }
    }


    fun scoreButton(){
        playerTotalScore += tempTotal
        compTotalScore += tempTotal2
        playerScoreText.text = "Player Score: $playerTotalScore"
        compScoreText.text = "Computer Score: $compTotalScore"
    }

    fun selectReroll(){
        //update the selectedDiceList array
        val diceIds = arrayOf(R.id.playerDice1, R.id.playerDice2, R.id.playerDice3, R.id.playerDice4, R.id.playerDice5)
        for (id in diceIds) {
            findViewById<ImageView>(id).setOnClickListener {
                it.alpha = 0.3f
                selectedDiceList.add(findViewById<ImageView>(id))

            }
        }


    }

    private fun computerDiceThrow(){
        var throwTotal = 0
        for (imageView in compDiceImageView) {
            val randomDiceFace = (1..6).random() // generate a random number between 1 and 6
            var diceImage = when (randomDiceFace) {
                1 -> R.drawable.face_one
                2 -> R.drawable.face_two
                3 -> R.drawable.face_three
                4 -> R.drawable.face_four
                5 -> R.drawable.face_five
                else -> R.drawable.face_six
            }
            imageView.setImageResource(diceImage) // set the image resource of the ImageView
            throwTotal += randomDiceFace
            compThrowTotal = throwTotal
        }
        tempTotal2 = throwTotal
    }

    private fun playerDiceThrow(){
        var throwTotal = 0
        for (imageView in playerDiceImageView) {
            val randomDiceFace = (1..6).random() // generate a random number between 1 and 6
            var diceImage = when (randomDiceFace) {
                1 -> R.drawable.face_one
                2 -> R.drawable.face_two
                3 -> R.drawable.face_three
                4 -> R.drawable.face_four
                5 -> R.drawable.face_five
                else -> R.drawable.face_six
            }
            // set the image resource of the ImageView if it is not in the selectedDiceList
            if(imageView !in selectedDiceList) {
                imageView.setImageResource(diceImage)
            } else {
                imageView.alpha = 1f
            }
            //get the total for this specific throw
            throwTotal += randomDiceFace
            playerThrowTotal = throwTotal
        }
        //set the throw total to the playerTotalScore if score is pressed
        tempTotal = throwTotal

    }


}
