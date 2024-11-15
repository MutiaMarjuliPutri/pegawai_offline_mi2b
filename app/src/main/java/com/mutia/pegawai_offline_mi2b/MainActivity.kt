package com.mutia.pegawai_offline_mi2b

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mutia.pegawai_offline_mi2b.helper.DBHelper
import java.util.jar.Attributes
class MainActivity : AppCompatActivity() {

    private lateinit var addName: Button
    private lateinit var printName: Button
    private lateinit var enterAge: EditText
    private lateinit var enterName: EditText
    private lateinit var name: TextView
    private lateinit var age: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        addName = findViewById(R.id.addName)
        printName = findViewById(R.id.printName)
        enterAge = findViewById(R.id.enterAge)
        enterName = findViewById(R.id.enterName)
        name = findViewById(R.id.Name)
        age = findViewById(R.id.Age)

        addName.setOnClickListener {
            val db = DBHelper(this, null)
            val nameInput = enterName.text.toString()
            val ageInput = enterAge.text.toString()

            db.addName(nameInput, ageInput)

            Toast.makeText(this, "$nameInput added to database", Toast.LENGTH_LONG).show()

            enterName.text.clear()
            enterAge.text.clear()
        }

        printName.setOnClickListener {
            val db = DBHelper(this, null)
            val cursor = db.getName()

            val nameBuilder = StringBuilder()
            val ageBuilder = StringBuilder()

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COl))
                    val age = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.AGE_COL))

                    nameBuilder.append(name).append("\n")
                    ageBuilder.append(age).append("\n")
                } while (cursor.moveToNext())

                name.text = nameBuilder.toString()
                age.text = ageBuilder.toString()
            }

            cursor?.close()

        }
    }
}