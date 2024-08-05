package com.example.todolistdemo;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistdemo.Adapter.ToDoAdapter;
import com.example.todolistdemo.Model.ToDoModel;
import com.example.todolistdemo.Utils.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity  implements OnDialogCloseListener{

    private RecyclerView mRecyclerview;
    private FloatingActionButton fab;
    private DatabaseHelper myDB;

    private List<ToDoModel> mlist;
    private ToDoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerview = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);
        myDB = new DatabaseHelper(MainActivity.this);
        mlist = new ArrayList<>();
        adapter = new ToDoAdapter(myDB, MainActivity.this);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(adapter);

        mlist  = myDB.getAllTasks();
        Collections.reverse(mlist);
        adapter.setTasks(mlist);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);

            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerview);



    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {

        mlist  = myDB.getAllTasks();
        Collections.reverse(mlist);
        adapter.setTasks(mlist);
        adapter.notifyDataSetChanged();

    }
}