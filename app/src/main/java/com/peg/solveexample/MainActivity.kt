package com.peg.solveexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.peg.solveexample.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    val signs = arrayOf("+","-", "*","/")
    lateinit var mainHandler : Handler
    var left = 0
    var right = 0
    var sign = "+"
    var trueCount = 0
    var falseCount = 0
    var allCount = 0
    private var secondsLeft:Int = 0
    private var RecordTime: Int = 10
    private var list: MutableList<Int> = mutableListOf()

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewMax.text = RecordTime.toString()
        mainHandler = Handler(Looper.getMainLooper())
    }

    private val updateText = object :Runnable{
        override fun run() {
            Second()
            mainHandler.postDelayed(this,1000)
        }
    }

    fun Start(view: View){

        super.onResume()
        mainHandler.post(updateText)
        mainHandler = Handler(Looper.getMainLooper())
        left = (10..99).random()
        right = (10..99).random()
        sign = signs.random()
        if(sign == "/"){
            left = left * right
        }
        binding.textViewLeftOperand.text = left.toString()
        binding.textViewRightOperand.text = right.toString()
        binding.textViewSign.text = sign
        binding.buttonStart.isEnabled = false
        binding.buttonTrue.isEnabled = true
        binding.buttonFalse.isEnabled = true
        binding.linearLayoutExample.setBackgroundColor(getColor(R.color.white))

        if (arrayOf(true, false).random()){
            when (binding.textViewSign.text) {
                "+" -> {
                    binding.textViewResult.text = Show().toInt().toString()
                }
                "-" -> {
                    binding.textViewResult.text = Show().toInt().toString()
                }
                "*" -> {
                    binding.textViewResult.text = Show().toInt().toString()
                }
                "/" -> {
                    binding.textViewResult.text = Show().toString()
                }
            }
        }
        else {
            var result = 0
            do {
                result = ((Show() - 50).toInt()until (Show() + 50).toInt()).random().toInt()
            } while (result == Show())
            binding.textViewResult.text = result.toString()
        }
    }

    private fun Show(): Int {
        var result = 0
        when (binding.textViewSign.text) {
            "+" -> {
                result = (binding.textViewLeftOperand.text.toString().toInt() + binding.textViewRightOperand.text.toString().toInt())
            }
            "-" -> {
                result = (binding.textViewLeftOperand.text.toString().toInt() - binding.textViewRightOperand.text.toString().toInt())
            }
            "*" -> {
                result = (binding.textViewLeftOperand.text.toString().toInt() * binding.textViewRightOperand.text.toString().toInt())
            }
            "/" -> {
                result = (binding.textViewLeftOperand.text.toString().toInt() / binding.textViewRightOperand.text.toString().toInt())
            }
        }
        return result
    }

    fun True(view: View) {
        if (Show() == binding.textViewResult.text.toString().toInt()){
            trueCount += 1
            binding.linearLayoutExample.setBackgroundColor(getColor(R.color.trueColor))
            RecordSecond()
        }
        else {
            falseCount += 1
            binding.linearLayoutExample.setBackgroundColor(getColor(R.color.falseColor))
        }
        allCount+=1
        Validate()
    }

    fun False(view: View) {
        if (Show() == binding.textViewResult.text.toString().toInt()){
            falseCount += 1
            binding.linearLayoutExample.setBackgroundColor(getColor(R.color.falseColor))
        }
        else {
            trueCount += 1
            binding.linearLayoutExample.setBackgroundColor(getColor(R.color.trueColor))
            RecordSecond()
        }
        allCount+=1
        Validate()
    }

    private fun Second(){
        secondsLeft++
        binding.textViewSeconds.text = secondsLeft.toString()
    }

    /*fun Check(view: View){
        var check = false
        when(sign){
            "+" -> {
                if(Validation.toString().toInt() == (left + right)){
                    trueCount+=1
                    check = true
                }
            }
            "-" -> {
                if(Validation.toString().toInt() == (left - right)){
                    trueCount+=1
                    check = true

                }
            }
            "*" -> {
                if(Validation.toString().toInt() == (left * right)){
                    trueCount+=1
                    check = true
                }
            }
            "/" -> {
                if(Validation.toString().toInt() == (left / right)){
                    trueCount+=1
                    check = true
                }
            }
        }
        if(check){
            binding.linearLayoutExample.setBackgroundColor(getColor(R.color.trueColor))
            RecordSecond()
        }
        else{
            binding.linearLayoutExample.setBackgroundColor(getColor(R.color.falseColor))
        }
        allCount+=1
        binding.buttonStart.isEnabled = true
        Validate()
    }*/


    fun Validate(){
        binding.buttonFalse.isEnabled = false
        binding.buttonTrue.isEnabled = false
        binding.buttonStart.isEnabled = true
        binding.textViewTrue.text = trueCount.toString()
        binding.textViewFalse.text = (allCount - trueCount).toString()
        binding.textViewAllCount.text = allCount.toString()
        binding.textViewPercent.text = "${String.format("%.2f",(100f / allCount.toFloat() * trueCount.toFloat()))}%"

        list.add(secondsLeft)
        var avg = 0
        for(i in list){
            avg+=i
        }
        binding.textViewAvg.text = (avg/list.size).toString()

        secondsLeft = 0
        binding.textViewSeconds.text = secondsLeft.toString()

        super.onPause()
        mainHandler.removeCallbacks(updateText)
    }

    private fun RecordSecond(){
        if(RecordTime > secondsLeft) {
            RecordTime = secondsLeft
            binding.textViewMax.text = RecordTime.toString()
        }
    }
}