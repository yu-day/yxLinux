package com.example.mylinux;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 请求权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }
                // 获取命令输入
                String command = editText.getText().toString().trim();
                if (!command.isEmpty()) { // 检查命令是否为空
                    try {
                        Process process = Runtime.getRuntime().exec(command); //使用Runtime.getRuntime().exec()方法执行Linux命令
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(process.getInputStream()));
                        StringBuilder output = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            output.append(line + "\n");
                        }
                        // 执行命令并输出结果
                        textView.setText(output.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else { // 如果命令为空，则清空TextView
                    textView.setText("");
                }
            }
        });

    }
}



