package com.example.yakovleva_zd2_v8

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CartActivity : AppCompatActivity() {

    private lateinit var rollItems: List<RollItem>
    private lateinit var listView: ListView
    private lateinit var deleteBtn: Button
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.list_view)
        deleteBtn = findViewById(R.id.delete_button)
        backBtn = findViewById(R.id.back_button)

        deleteBtn.setOnClickListener {
            val position = listView.checkedItemPosition
            if (position != ListView.INVALID_POSITION) {
                deleteItem(position)
            } else {
                Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show()
            }
        }

        backBtn.setOnClickListener {
            finish()
        }
        loadRolls()
    }
    private fun loadRolls() {
        try {
            val db = AppDatabase.getInstance(this)
            rollItems = db.rollDao().getAll()

            val items = mutableListOf<String>()

            if (rollItems.isEmpty()) {
                items.add("Корзина пуста")
            } else {
                for (roll in rollItems) {
                    val wishes = roll.notes ?: "без пожеланий"
                    items.add("${roll.name} ${roll.quantity} шт ($wishes)")
                }
            }

            val adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_single_choice,
                items
            )
            listView.adapter = adapter
            listView.choiceMode = ListView.CHOICE_MODE_SINGLE

        }
        catch (e: Exception) {
            Toast.makeText(this, "Ошибка загрузки", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteItem(position: Int) {
        try {
            val itemToDelete = rollItems[position]
            val db = AppDatabase.getInstance(this)
            db.rollDao().delete(itemToDelete)

            Toast.makeText(this, "Удалено: ${itemToDelete.name}", Toast.LENGTH_SHORT).show()
            loadRolls()
        }
        catch (e: Exception) {
            Toast.makeText(this, "Ошибка удаления", Toast.LENGTH_SHORT).show()
        }
    }
}