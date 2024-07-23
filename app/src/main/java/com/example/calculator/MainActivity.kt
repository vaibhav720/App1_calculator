package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var textViewResult: TextView
    private var currentInput = ""
    private var operator = ""
    private var firstInput = ""
    private var secondInput = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewResult = findViewById(R.id.ResultText)

        setNumberButtonListeners()
        setOperatorButtonListeners()
    }

    private fun setNumberButtonListeners() {
        val numberButtons = listOf(
            R.id.buttonZero, R.id.buttonOne, R.id.buttonTwo, R.id.buttonThree,
            R.id.buttonFour, R.id.buttonFive, R.id.buttonSix, R.id.buttonSeven,
            R.id.buttonEight, R.id.buttonNine
        )

        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                onNumberClicked(it)
            }
        }
    }

    private fun setOperatorButtonListeners() {
        val operatorButtons = listOf(
            R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply,
            R.id.buttonDivide, R.id.buttonEquals, R.id.buttonClear, R.id.buttonDot
        )

        for (id in operatorButtons) {
            findViewById<Button>(id).setOnClickListener {
                onOperationClicked(it)
            }
        }
    }

    fun onNumberClicked(view: View) {
        val button = view as Button
        currentInput += button.text
        textViewResult.text = currentInput
    }

    fun onOperationClicked(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()

        when (buttonText) {
            "C" -> {
                currentInput = ""
                operator = ""
                firstInput = ""
                secondInput = ""
                textViewResult.text = "0"
            }
            "+", "-", "*", "/" -> {
                if (currentInput.isNotEmpty()) {
                    firstInput = currentInput
                    operator = buttonText
                    currentInput = ""
                }
            }
            "=" -> {
                if (firstInput.isNotEmpty() && operator.isNotEmpty() && currentInput.isNotEmpty()) {
                    secondInput = currentInput
                    val result = calculateResult()
                    textViewResult.text = result.toString()
                    currentInput = result.toString()
                    firstInput = ""
                    operator = ""
                    secondInput = ""
                }
            }
            "." -> {
                if (!currentInput.contains(".")) {
                    currentInput += "."
                    textViewResult.text = currentInput
                }
            }
        }
    }

    private fun calculateResult(): Double {
        val num1 = firstInput.toDouble()
        val num2 = secondInput.toDouble()

        return when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN
            else -> Double.NaN
        }
    }
}