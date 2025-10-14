package com.example.test3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.test2.R

class MainActivity : AppCompatActivity() {
    // 实验ListView所需：动物名称+图片资源
    private val animalNames = arrayOf("Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant")
    private val animalImages = intArrayOf(
        R.drawable.lion,
        R.drawable.tiger,
        R.drawable.monkey,
        R.drawable.dog,
        R.drawable.cat,
        R.drawable.elephant
    )

    // 实验所需控件
    private lateinit var lvAnimal: ListView
    private lateinit var tvTest: TextView
    private lateinit var notificationManager: NotificationManager
    private val channelId = "animal_notification_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvAnimal = findViewById(R.id.lv_animal)
        tvTest = findViewById(R.id.tv_test)
        initNotificationChannel()

        // 构建ListView数据
        val dataList = mutableListOf<Map<String, Any>>()
        for (i in animalNames.indices) {
            val map = mutableMapOf<String, Any>().apply {
                put("image", animalImages[i])
                put("name", animalNames[i])
            }
            dataList.add(map)
        }

        val adapter = SimpleAdapter(
            this,
            dataList,
            R.layout.item_listview,
            arrayOf("image", "name"),
            intArrayOf(R.id.iv_animal, R.id.tv_animal_name)
        )
        lvAnimal.adapter = adapter

        // ListView项点击事件
        lvAnimal.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedAnimal = animalNames[position]
            Toast.makeText(this, "选中：$selectedAnimal", Toast.LENGTH_SHORT).show()
            sendAnimalNotification(selectedAnimal)
        }

        // ListView长按事件
        lvAnimal.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, position, _ ->
            startActionMode(object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    mode?.menuInflater?.inflate(R.menu.menu_context, menu)
                    mode?.title = "1 selected: ${animalNames[position]}"
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    return if (item?.itemId == R.id.menu_context_delete) {
                        Toast.makeText(this@MainActivity, "已删除：${animalNames[position]}", Toast.LENGTH_SHORT).show()
                        mode?.finish()
                        true
                    } else false
                }

                override fun onDestroyActionMode(mode: ActionMode?) {}
            })
            true
        }
    }

    // 初始化通知渠道
    private fun initNotificationChannel() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "动物列表通知",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "点击ListView项时发送的通知"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    // 发送通知
    private fun sendAnimalNotification(animalName: String) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("列表项选中通知")
            .setContentText("你选中了动物：$animalName")
            .setAutoCancel(true)
            .build()
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    // 显示自定义登录对话框
    private fun showCustomLoginDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_login, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val etUsername = dialogView.findViewById<TextView>(R.id.et_username)
        val etPassword = dialogView.findViewById<TextView>(R.id.et_password)
        val btnCancel = dialogView.findViewById<View>(R.id.btn_cancel)
        val btnSignIn = dialogView.findViewById<View>(R.id.btn_signin)

        btnCancel.setOnClickListener { alertDialog.dismiss() }
        btnSignIn.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "登录成功！用户名：$username", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }

    // 创建XML菜单
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // 菜单点击逻辑
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_size_small -> {
                tvTest.textSize = 10f
                Toast.makeText(this, "字体：小（10号）", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_size_medium -> {
                tvTest.textSize = 16f
                Toast.makeText(this, "字体：中（16号）", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_size_large -> {
                tvTest.textSize = 20f
                Toast.makeText(this, "字体：大（20号）", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_normal -> {
                Toast.makeText(this, "点击普通菜单项", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_color_red -> {
                tvTest.setTextColor(ContextCompat.getColor(this, R.color.red))
                Toast.makeText(this, "字体颜色：红色", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_color_black -> {
                tvTest.setTextColor(ContextCompat.getColor(this, R.color.black))
                Toast.makeText(this, "字体颜色：黑色", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_show_dialog -> {
                showCustomLoginDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}