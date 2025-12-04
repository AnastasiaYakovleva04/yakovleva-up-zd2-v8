package com.example.yakovleva_zd2_v8

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

class AddRollActivity : AppCompatActivity() {

    private lateinit var addBtn: Button
    private lateinit var backBtn: Button
    private lateinit var rollsSpinner: Spinner
    private lateinit var quantityInput: EditText
    private lateinit var notesInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_roll)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rollsSpinner = findViewById(R.id.rolls_spinner)
        quantityInput = findViewById(R.id.quantity_input)
        notesInput = findViewById(R.id.notes_input)
        addBtn = findViewById(R.id.add_button)
        backBtn = findViewById(R.id.back_button)

        val rollsArray = resources.getStringArray(R.array.rolls_array)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            rollsArray
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rollsSpinner.adapter = adapter
        rollsSpinner.setSelection(0)

        addBtn.setOnClickListener {
            addRollToDatabase()
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    //добавление ролла в бд
    private fun addRollToDatabase() {

        val selectedRoll = rollsSpinner.selectedItem.toString()
        val quantityText = quantityInput.text.toString().trim()
        val notes = notesInput.text.toString().trim()


        if (quantityText.isEmpty()) {
            showSnackBar(findViewById(android.R.id.content), "Введите количество")
            return
        }

        val quantity = quantityText.toIntOrNull()
        if (quantity == null || quantity <= 0) {
            showSnackBar(findViewById(android.R.id.content), "Количество должно быть больше 0")
            return
        }

        try {
            val rollItem = RollItem(
                name = selectedRoll,
                quantity = quantity,
                notes = if (notes.isEmpty()) null else notes
            )

            val db = AppDatabase.getInstance(this)
            db.rollDao().insert(rollItem)

            showSnackBar(findViewById(android.R.id.content), "Добавлено в корзину")

            quantityInput.text.clear()
            notesInput.text.clear()

        }
        catch (e: Exception) {
            showSnackBar(findViewById(android.R.id.content), "Ошибка: ${e.message}")
        }
    }
    //показ снекбара
    private fun showSnackBar(view: View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.cream))
        snackbar.setTextColor(ContextCompat.getColor(this, R.color.green))
        snackbar.show()
    }
}