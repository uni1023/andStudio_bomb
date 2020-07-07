package com.example.bombgame

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var initialCountDown : Long = 0
    private lateinit var countDownTimer : CountDownTimer
    private lateinit var bombAnimation : AnimationDrawable
    private val countDownInterval : Long = 1000
    private var timeLeftOnTimer : Long = 0
    private var gameStarted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. 여기에 이미지 버튼을 눌렀는지 귀를 기울이는 (setOnClickListener)를 만든다
        imageButtonBomb.setOnClickListener { startGame() }

    }
    // 2. 여기에 이미지 버튼을 눌렀을때 실행할 함수(startGame)를 만든다.

    private fun startGame() {

        if (!gameStarted) {
            imageButtonBomb.visibility = View.GONE
            imageViewAnim.visibility = View.VISIBLE
            bombAnim()
            countDown()
        } else {
            imageButtonBomb.setImageResource(R.drawable.bomb)
            Handler().postDelayed({resetGame()}, 1000)
            resetGame()
        }

        gameStarted = true
    }




    // 3. 여기에 타이머가 끝났을때 실행할 함수(endGame)를 만든다.
    private fun endGame(){ // 패키지 or 클래스 안에서만 접근 가능

        bombAnimation.stop()

        imageViewAnim.visibility = View.GONE
        imageViewAnim.visibility = View.VISIBLE
        imageButtonBomb.setImageResource(R.drawable.explosion)

    }

    private fun resetGame() {
        gameStarted = false
        startGame()
    }

    private fun countDown(){
        val randomCount = Random.nextInt(5) + 2 // 카운트 수 : 테스트 중
        textViewTime.text = getString(R.string.text_view_time, randomCount)

        initialCountDown = randomCount.toLong() * 1000
        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {

            override fun onFinish(){
                endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished /  1000
                textViewTime.text = getString(R.string.text_view_time, timeLeftOnTimer)

            }

        }
        countDownTimer.start()
    }

    private fun bombAnim(){
        imageViewAnim.apply {
            setBackgroundResource(R.drawable.bomb_animation)
            bombAnimation = background as AnimationDrawable
        }
            bombAnimation.start()
        }
    }