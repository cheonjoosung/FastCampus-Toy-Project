package com.example.fastcampus.part1.ch04_calculator

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding

    private val firstNumberText = StringBuilder("")
    private val secondNumberText = StringBuilder("")
    private val operatorText = StringBuilder("")

    //private val decimalFormat = DecimalFormat("#,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btn0.setOnClickListener(listener)
            btn1.setOnClickListener(listener)
            btn2.setOnClickListener(listener)
            btn3.setOnClickListener(listener)
            btn4.setOnClickListener(listener)
            btn5.setOnClickListener(listener)
            btn6.setOnClickListener(listener)
            btn7.setOnClickListener(listener)
            btn8.setOnClickListener(listener)
            btn9.setOnClickListener(listener)
            btnClear.setOnClickListener(listener)
            btnMod.setOnClickListener(listener)
            btnMul.setOnClickListener(listener)
            btnDivide.setOnClickListener(listener)
            btnAdd.setOnClickListener(listener)
            btnMinus.setOnClickListener(listener)
            btnEqual.setOnClickListener(listener)
            btnDelete.setOnClickListener(listener)
        }
    }

    private val listener = View.OnClickListener {
        when (it.id) {
            R.id.btn_0 -> numberClicked(0)
            R.id.btn_1 -> numberClicked(1)
            R.id.btn_2 -> numberClicked(2)
            R.id.btn_3 -> numberClicked(3)
            R.id.btn_4 -> numberClicked(4)
            R.id.btn_5 -> numberClicked(5)
            R.id.btn_6 -> numberClicked(6)
            R.id.btn_7 -> numberClicked(7)
            R.id.btn_8 -> numberClicked(8)
            R.id.btn_9 -> numberClicked(9)
            R.id.btn_mul -> operatorClicked("*")
            R.id.btn_divide -> operatorClicked("/")
            R.id.btn_mod -> operatorClicked("%")
            R.id.btn_add -> operatorClicked("+")
            R.id.btn_minus -> operatorClicked("-")
            R.id.btn_clear -> clearClicked()
            R.id.btn_delete -> clearClicked()
            R.id.btn_equal -> equalClicked()
        }
    }

    private fun numberClicked(num: Int) {
        val numberString = num.toString()
        val numberText = if (operatorText.isEmpty()) firstNumberText else secondNumberText

        if ((operatorText.toString() == "/" || operatorText.toString() == "%") && numberString == "0") {
            Toast.makeText(this, getString(R.string.msg_no_divide_zero), Toast.LENGTH_SHORT).show()
            return
        } else {
            numberText.append(numberString)
            updateUI()
        }
    }

    private fun operatorClicked(op: String) {

        if (firstNumberText.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_number_input_need), Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (secondNumberText.isNotEmpty()) {
            Toast.makeText(this, getString(R.string.msg_only_one_operator), Toast.LENGTH_SHORT)
                .show()
            return
        }

        operatorText.append(op)
        updateUI()
    }

    private fun equalClicked() {

        if (firstNumberText.isEmpty() || secondNumberText.isEmpty() || operatorText.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_expression_error), Toast.LENGTH_SHORT).show()
            return
        }

        val firstNumber = firstNumberText.toString().toBigDecimal()
        val secondNumber = secondNumberText.toString().toBigDecimal()

        val result = when (operatorText.toString()) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> firstNumber / secondNumber
            "%" -> firstNumber % secondNumber
            else -> 0
        }.toString()

        binding.tvResult.text = result
        firstNumberText.clear()
        secondNumberText.clear()
        operatorText.clear()
        if (result != "0") firstNumberText.append(result)
        updateUI()
    }

    private fun clearClicked() {
        firstNumberText.clear()
        secondNumberText.clear()
        operatorText.clear()
        binding.tvResult.text = ""
        updateUI()
    }

    private fun updateUI() {
        val equation = "$firstNumberText $operatorText $secondNumberText"
        binding.tvEquation.text = equation
    }
}