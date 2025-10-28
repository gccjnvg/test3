一、ListView 使用
资源准备：从课程群附件下载列表项所需图片，复制到 “res/drawable” 目录下；准备列表数据（动物名称 + 对应图片 id，如 “Lion” 对应 “R.drawable.lion”）。
编写列表项布局：在 “res/layout” 目录下新建 XML 文件，命名为 “item_listview.xml”，根布局设为 LinearLayout（horizontal 方向），内部添加：
ImageView：id 设为 “iv_animal”，layout_width/layout_height 设为 “50dp”，scaleType 设为 “centerCrop”；
TextView：id 设为 “tv_animal”，layout_width 设为 “wrap_content”，layout_height 设为 “match_parent”，textSize 设为 “16sp”，gravity 设为 “center_vertical”，marginStart 设为 “10dp”。
编写主布局：在 “res/layout” 目录下新建 “activity_listview.xml”，根布局设为 LinearLayout（vertical 方向），内部添加 ListView（id 设为 “lv_animal”，layout_width/layout_height 设为 “match_parent”）。
编写 Activity 代码（以 MainActivity 为例）：
定义数据集合：创建 ArrayList<HashMap<String, Object>>，添加 6 条数据（每条数据含 “image”（图片 id）和 “name”（动物名称）键值对）；
创建 SimpleAdapter：实例化 SimpleAdapter，参数依次为 “this”（上下文）、数据集合、列表项布局 id（R.layout.item_listview）、新键数组（new String []{"image", "name"}）、对应控件 id 数组（new int []{R.id.iv_animal, R.id.tv_animal}）；
为 ListView 设置适配器：lv_animal.setAdapter (simpleAdapter)；
添加列表项点击事件：
lv_animal.setOnItemClickListener ((parent, view, position, id) -> {
// 弹出 Toast：获取选中项名称
String animalName = ((HashMap<String, Object>) parent.getItemAtPosition (position)).get ("name").toString ();
Toast.makeText (MainActivity.this, "你选中了：" + animalName, Toast.LENGTH_SHORT).show ();
// 发送通知：创建 NotificationManager，设置通知图标（群附件资源）、标题、内容
NotificationManager notificationManager = (NotificationManager) getSystemService (Context.NOTIFICATION_SERVICE);
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
NotificationChannel channel = new NotificationChannel ("lab3_channel", "ListView 通知", NotificationManager.IMPORTANCE_DEFAULT);
notificationManager.createNotificationChannel (channel);
}
Notification notification = new NotificationCompat.Builder (this, "lab3_channel")
.setSmallIcon (R.drawable.app_icon) 
.setContentTitle ("ListView 选中通知")
.setContentText ("你刚刚选中了：" + animalName)
.setAutoCancel (true)
.build ();
notificationManager.notify (1, notification);
});
//权限配置：在 “AndroidManifest.xml” 中添加通知权限）：<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />，并在 Activity 中动态申请权限。


二、创建自定义布局的 AlertDialog
编写自定义对话框布局：在 “res/layout” 目录下新建 XML 文件，命名为 “dialog_login.xml”，根布局设为 LinearLayout（vertical 方向），内部添加：
TextView：文本设为 “ANDROID APP”，textSize 设为 “18sp”，textStyle 设为 “bold”，gravity 设为 “center”，marginBottom 设为 “15dp”；
EditText：hint 设为 “Username”，id 设为 “et_username”，layout_width 设为 “match_parent”，layout_height 设为 “wrap_content”，marginStart/End 设为 “20dp”，marginBottom 设为 “10dp”；
EditText：hint 设为 “Password”，id 设为 “et_password”，inputType 设为 “textPassword”，其他属性同用户名输入框；
LinearLayout：layout_width 设为 “match_parent”，layout_height 设为 “wrap_content”，marginTop 设为 “15dp”，内部添加：
Button：文本设为 “Cancel”，id 设为 “btn_cancel”，layout_weight 设为 “1”，marginEnd 设为 “5dp”；
Button：文本设为 “Sign in”，id 设为 “btn_signin”，layout_weight 设为 “1”，marginStart 设为 “5dp”。
编写 Activity 代码：
在主布局中添加一个 Button（文本设为 “显示登录对话框”，id 设为 “btn_show_dialog”）；
在 Activity 中为该按钮设置点击事件：
btn_show_dialog.setOnClickListener (v -> {
// 加载自定义布局
View dialogView = LayoutInflater.from (this).inflate (R.layout.dialog_login, null);
// 创建 AlertDialog
AlertDialog alertDialog = new AlertDialog.Builder (this)
.setView (dialogView)
.create ();
// 为对话框内的 Cancel 按钮设置点击事件
dialogView.findViewById (R.id.btn_cancel).setOnClickListener (v1 -> alertDialog.dismiss ());
// 为 Sign in 按钮设置点击事件
dialogView.findViewById (R.id.btn_signin).setOnClickListener (v1 -> {
String username = ((EditText) dialogView.findViewById (R.id.et_username)).getText ().toString ();
String password = ((EditText) dialogView.findViewById (R.id.et_password)).getText ().toString ();
Toast.makeText (MainActivity.this, "用户名：" + username + "，密码：" + password, Toast.LENGTH_SHORT).show ();
alertDialog.dismiss ();
});
// 显示对话框
alertDialog.show ();
});


三、使用 XML 定义菜单
创建菜单 XML 文件：在 “res” 目录下新建 “menu” 文件夹，在 menu 文件夹下新建 XML 文件，命名为 “menu_text_style.xml”，代码如下：
xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- 字体大小子菜单 -->
    <item
        android:id="@+id/item_text_size"
        android:title="字体大小">
        <menu>
            <item android:id="@+id/size_small" android:title="小（10号）" />
            <item android:id="@+id/size_middle" android:title="中（16号）" />
            <item android:id="@+id/size_large" android:title="大（20号）" />
        </menu>
    </item>
    <!-- 普通菜单项 -->
    <item
        android:id="@+id/item_normal"
        android:title="普通菜单项" />
    <!-- 字体颜色子菜单 -->
    <item
        android:id="@+id/item_text_color"
        android:title="字体颜色">
        <menu>
            <item android:id="@+id/color_red" android:title="红色" />
            <item android:id="@+id/color_black" android:title="黑色" />
        </menu>
    </item>
</menu>
编写主布局：在 “res/layout” 目录下新建 “activity_menu.xml”，根布局设为 LinearLayout（vertical 方向），内部添加 TextView（id 设为 “tv_test”，文本设为 “用于测试的内容”，textSize 设为 “16sp”，layout_margin 设为 “20dp”）。
编写 Activity 代码：
重写 onCreateOptionsMenu 方法，加载菜单：
@Override
public boolean onCreateOptionsMenu (Menu menu) {
getMenuInflater ().inflate (R.menu.menu_text_style, menu);
return true;
}
重写 onOptionsItemSelected 方法，处理菜单点击：
@Override
public boolean onOptionsItemSelected (@NonNull MenuItem item) {
TextView tvTest = findViewById (R.id.tv_test);
int itemId = item.getItemId ();
if (itemId == R.id.size_small) {
tvTest.setTextSize (TypedValue.COMPLEX_UNIT_SP, 10); // 10 号字（SP 单位，适配屏幕）
} else if (itemId == R.id.size_middle) {
tvTest.setTextSize (TypedValue.COMPLEX_UNIT_SP, 16);
} else if (itemId == R.id.size_large) {
tvTest.setTextSize (TypedValue.COMPLEX_UNIT_SP, 20);
} else if (itemId == R.id.item_normal) {
Toast.makeText (this, "你点击了普通菜单项", Toast.LENGTH_SHORT).show ();
} else if (itemId == R.id.color_red) {
tvTest.setTextColor (Color.RED);
} else if (itemId == R.id.color_black) {
tvTest.setTextColor (Color.BLACK);
}
return true;
}
测试：运行模拟器，点击屏幕右上角的菜单按钮，展开菜单后点击对应选项，查看 TextView 的字体大小 / 颜色是否变化、Toast 是否弹出。

              
四、创建 ActionMode 的上下文菜单
编写主布局：在 “res/layout” 目录下新建 “activity_actionmode.xml”，根布局设为 LinearLayout（vertical 方向），内部添加 ListView（id 设为 “lv_actionmode”，layout_width/layout_height 设为 “match_parent”）。
编写 Activity 代码：
定义列表数据：String [] data = {"One", "Two", "Three", "Four", "Five"}；
为 ListView 设置适配器：使用 ArrayAdapter，
代码：
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class ActionModeMenuActivity extends AppCompatActivity {
    // 定义列表数据
    private String[] data = {"One", "Two", "Three", "Four", "Five"};
    private ListView lvActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 关联布局文件
        setContentView(R.layout.activity_action_mode);
        
        // 初始化ListView并绑定布局中的控件ID
        lvActionMode = findViewById(R.id.lv_actionmode);
        
        // 为ListView设置ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1, 
                data
        );
        lvActionMode.setAdapter(adapter);
        
        // 设置ListView为多选模式，启用ActionMode上下文菜单
        lvActionMode.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvActionMode.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            
            private int selectedCount = 0;

      
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // 加载XML定义的菜单）
                mode.getMenuInflater().inflate(R.menu.menu_text_style, menu);
                // 设置ActionMode初始标题
                mode.setTitle(selectedCount + " selected");
                return true; 
            }

            // ActionMode准备时调用
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            // ActionMode菜单选项点击时调用
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // 可根据菜单ID补充具体逻辑
                switch (item.getItemId()) {
                    case R.id.item_text_size:
                        break;
                    case R.id.item_normal:
                        break;
                        break;
                }
                // 处理完成后关闭ActionMode
                mode.finish();
                return true;
            }

            // 列表项选中状态变化时调用
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // 根据选中状态更新计数
                if (checked) {
                    selectedCount++;
                } else {
                    selectedCount--;
                }
               
                mode.setTitle(selectedCount + " selected");
            }

            // ActionMode销毁时调用
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                selectedCount = 0; 
            }
        });
    }
}
