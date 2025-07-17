package edu.csueb.android.zoodirectory

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animals = listOf(
            Animal("Elephant", "Large mammal with trunk", R.drawable.elephant_thumb, R.drawable.elephant_large),
            Animal("Lion", "King of the jungle", R.drawable.lion_thumb, R.drawable.lion_large),
            Animal("Giraffe", "Tallest land animal", R.drawable.giraffe_thumb, R.drawable.giraffe_large),
            Animal("Monkey", "Playful primate", R.drawable.monkey_thumb, R.drawable.monkey_large),
            Animal("Tiger", "Fierce striped cat", R.drawable.tiger_thumb, R.drawable.tiger_large)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AnimalAdapter(this, animals)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.info -> {
                startActivity(Intent(this, ZooInfoActivity::class.java))
                true
            }
            R.id.uninstall -> {
                val packageUri = Uri.parse("package:$packageName")
                startActivity(Intent(Intent.ACTION_DELETE, packageUri))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}