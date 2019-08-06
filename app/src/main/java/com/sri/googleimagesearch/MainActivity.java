package com.sri.googleimagesearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sri.googleimagesearch.util.Constants;
import com.sri.googleimagesearch.util.HttpClientFacade;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchText = findViewById(R.id.search_text);
    }


    public void search(final View view) throws IOException, URISyntaxException {
        final String searchKeyword = searchText.getText().toString();
        System.out.println("Search Keyword : " + searchKeyword);

        Intent intent = new Intent(getApplicationContext(), ImageListActivity.class);
        intent.putExtra("keyword", searchKeyword);
        startActivity(intent);

    }


}
