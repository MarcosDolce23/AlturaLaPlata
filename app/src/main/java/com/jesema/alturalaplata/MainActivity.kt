package com.jesema.alturalaplata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputStreet: EditText = findViewById(R.id.input_street)
        val inputNumber: EditText = findViewById(R.id.input_number)
        val outputResult: TextView = findViewById(R.id.output_result)
        val calculate: Button = findViewById(R.id.calculate)

        val textLayoutStreet: TextInputLayout = findViewById(R.id.text_input_layout_street)
        val textLayoutNumber: TextInputLayout = findViewById(R.id.text_input_layout_number)

        inputStreet.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textLayoutStreet.error = ""
                outputResult.visibility = View.INVISIBLE
            }
        })

        inputNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textLayoutNumber.error = ""
                outputResult.visibility = View.INVISIBLE
            }
        })

        calculate.setOnClickListener {

            outputResult.visibility = View.INVISIBLE
            textLayoutStreet.error = ""
            textLayoutNumber.error = ""

            val street: String =  inputStreet.text.toString()
            val number: String = inputNumber.text.toString()

            if (street.isEmpty()) {
                textLayoutStreet.error = getString(R.string.emptyStreet)
            }

            if (number.isEmpty()) {
                textLayoutNumber.error = getString(R.string.emptyNumber)
                return@setOnClickListener
            }

            //val topDigitsStreet: Int = street[0].toString().toInt()
            val halfDigit: Int = if (number.length > 1) { number[number.length-2].toString().toInt() } else { 0 }

            var firstStreet = 0

            val topDigitsNumber: Int = if (number.length < 4) {
                number[0].toString().toInt()
            } else {
                number.subSequence(0..1).toString().toInt()
            }

            if (street.toInt() in 1..80 && street.toInt() != 52) {

                // Streets parallel to 50
                if (street.toInt() in 32..72) {
                    if (number.toInt() < 1800) {
                        if (number.toInt() >= 300) {
                            firstStreet = if (halfDigit < 5) {
                                topDigitsNumber * 2 - 5
                            } else {
                                topDigitsNumber * 2 - 4
                            }
                        } else {
                            textLayoutNumber.error = getString(R.string.outOfRangeNumber)
                        }
                    } else {
                        textLayoutNumber.error = getString(R.string.outOfRangeNumber)
                    }
                }

                // Streets parallel to 1
                if (street.toInt() in 1..31) {
                    firstStreet = if (number.toInt() < 100) {
                        if (number.toInt() < 50) {
                            32
                        } else {
                            33
                        }
                    } else {
                        if (halfDigit < 5) {
                            topDigitsNumber * 2 + 32
                        } else {
                            topDigitsNumber * 2 + 33
                        }
                    }
                    if (firstStreet >= 52) {
                        firstStreet++
                    }
                }

                // Diagonals 73 and 74
                if (street.toInt() == 73 || street.toInt() == 74) {
                    if (number.toInt() in 650..3599) {
                        firstStreet = topDigitsNumber - 5
                    }
                }

                // Diagonals 79 and 80
                if (street.toInt() == 79 || street.toInt() == 80) {
                    if (number.toInt() in 600..1099) {
                        firstStreet = topDigitsNumber - 5
                    }
                }

                // Diagonals 75 y 76
                if (street.toInt() == 75 || street.toInt() == 76) {
                    if (number.toInt() in 1..1099) {
                        firstStreet = if (number.toInt() in 1..99) {
                            14
                        } else {
                            topDigitsNumber + 14
                        }
                    }
                }

                // Diagonals 77 y 78
                if (street.toInt() == 77 || street.toInt() == 78) {
                    if (number.toInt() in 1..1099) {
                        firstStreet = if (number.toInt() in 1..99) {
                            1
                        } else {
                            topDigitsNumber + 1
                        }
                    }
                }

                val secondStreet: Int = if (firstStreet == 51) {
                    firstStreet + 2
                } else {
                    firstStreet + 1
                }

                if (firstStreet < 1 || firstStreet > 71) {
                    textLayoutNumber.error = getString(R.string.outOfRangeNumber)
                    return@setOnClickListener
                }

                outputResult.text = getString(R.string.result, firstStreet, secondStreet)
                outputResult.visibility = View.VISIBLE

            } else {
                textLayoutStreet.error = getString(R.string.outOfRangeStreet)
            }

        }
    }

}