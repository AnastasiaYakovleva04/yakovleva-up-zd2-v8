package com.example.yakovleva_zd2_v8

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast

class AddRollActivity : AppCompatActivity() {

    private lateinit var addBtn: Button
    private lateinit var backBtn: Button
    private lateinit var nameInput: EditText
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

        addBtn = findViewById(R.id.add_button)
        backBtn = findViewById(R.id.back_button)

        addBtn.setOnClickListener {
            addRollToDatabase()
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
    private fun addRollToDatabase() {
        nameInput = findViewById(R.id.name_input)
        quantityInput = findViewById(R.id.quantity_input)
        notesInput = findViewById(R.id.notes_input)

        val name = nameInput.text.toString().trim()
        val quantityText = quantityInput.text.toString().trim()
        val notes = notesInput.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "Введите название", Toast.LENGTH_SHORT).show()
            return
        }

        if (quantityText.isEmpty()) {
            Toast.makeText(this, "Введите количество", Toast.LENGTH_SHORT).show()
            return
        }

        val quantity = quantityText.toIntOrNull()
        if (quantity == null || quantity <= 0) {
            Toast.makeText(this, "Количество должно быть больше 0", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val rollItem = RollItem(
                name = name,
                quantity = quantity,
                notes = if (notes.isEmpty()) null else notes
            )

            val db = AppDatabase.getInstance(this)
            db.rollDao().insert(rollItem)

            Toast.makeText(this, "Добавлено в корзину", Toast.LENGTH_SHORT).show()

            nameInput.text.clear()
            quantityInput.text.clear()
            notesInput.text.clear()

        } catch (e: Exception) {
            Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}