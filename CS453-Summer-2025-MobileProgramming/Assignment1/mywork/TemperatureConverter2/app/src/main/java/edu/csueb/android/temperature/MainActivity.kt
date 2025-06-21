package edu.csueb.android.temperature

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var text: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.editTextText)
    }

    fun onClick(view: View) {
        if (view.id == R.id.button) {
            val celsiusButton = findViewById<RadioButton>(R.id.radioButton2)
            val fahrenheitButton = findViewById<RadioButton>(R.id.radioButton3)

            if (text.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show()
                return
            }

            val inputValue = text.text.toString().toFloat()

            if (celsiusButton.isChecked) {
                val result = ConverterUtil.convertFahrenheitToCelsius(inputValue)
                text.setText(result.toString())
                celsiusButton.isChecked = false
                fahrenheitButton.isChecked = true
            } else {
                val result = ConverterUtil.convertCelsiusToFahrenheit(inputValue)
                text.setText(result.toString())
                fahrenheitButton.isChecked = false
                celsiusButton.isChecked = true
            }
        }
    }
}
