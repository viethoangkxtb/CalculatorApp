package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var currentInput = ""
    private var operator: String? = null
    private var firstNumber: Int? = null
    private var isNegative = false  // Biến kiểm tra số âm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        val numberButtons = listOf(
            findViewById<Button>(R.id.btn0),
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4),
            findViewById<Button>(R.id.btn5),
            findViewById<Button>(R.id.btn6),
            findViewById<Button>(R.id.btn7),
            findViewById<Button>(R.id.btn8),
            findViewById<Button>(R.id.btn9)
        )

        val operatorButtons = mapOf(
            R.id.btnAdd to "+",
            R.id.btnSub to "-",
            R.id.btnMul to "*",
            R.id.btnDiv to "/"
        )

        val btnEqual = findViewById<Button>(R.id.btnEqual)
        val btnClear = findViewById<Button>(R.id.btnC)
        val btnNegative = findViewById<Button>(R.id.btnSub) // Dùng nút '-' để nhập số âm

        // Xử lý nhập số
        numberButtons.forEach { button ->
            button.setOnClickListener {
                currentInput += button.text
                tvResult.text = currentInput
            }
        }

        // Xử lý khi bấm toán tử
        operatorButtons.forEach { (id, op) ->
            findViewById<Button>(id).setOnClickListener {
                if (currentInput.isNotEmpty() || (op == "-" && firstNumber == null)) {
                    if (op == "-" && currentInput.isEmpty()) {
                        // Nếu chưa có số nào và bấm "-", cho phép nhập số âm
                        currentInput = "-"
                        tvResult.text = currentInput
                        isNegative = true
                    } else {
                        firstNumber = currentInput.toIntOrNull()
                        operator = op
                        currentInput = ""
                        isNegative = false
                    }
                }
            }
        }

        // Xử lý khi bấm "="
        btnEqual.setOnClickListener {
            if (currentInput.isNotEmpty() && firstNumber != null && operator != null) {
                val secondNumber = currentInput.toIntOrNull()
                val result = when (operator) {
                    "+" -> firstNumber!! + secondNumber!!
                    "-" -> firstNumber!! - secondNumber!!
                    "*" -> firstNumber!! * secondNumber!!
                    "/" -> if (secondNumber != 0) firstNumber!! / secondNumber!! else "Error"
                    else -> "Error"
                }
                tvResult.text = result.toString()
                currentInput = result.toString()
                firstNumber = null
                operator = null
                isNegative = false
            }
        }

        // Xử lý khi bấm "C" (clear)
        btnClear.setOnClickListener {
            currentInput = ""
            firstNumber = null
            operator = null
            isNegative = false
            tvResult.text = "0"
        }
    }
}