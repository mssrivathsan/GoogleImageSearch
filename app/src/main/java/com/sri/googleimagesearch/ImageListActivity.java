package com.sri.googleimagesearch;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sri.googleimagesearch.adapters.ImageListsAdapter;
import com.sri.googleimagesearch.model.Item;
import com.sri.googleimagesearch.model.SearchResult;
import com.sri.googleimagesearch.util.Constants;
import com.sri.googleimagesearch.util.HttpClientFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImageListActivity extends AppCompatActivity {

    private static final String TAG = ImageListActivity.class.getSimpleName();

    private String keyword = "";

    private static HttpClientFacade httpClientFacade;

    private RecyclerView imagesList;

    private Button nextPage;

    private Button previousPage;

    private ImageListsAdapter imageListsAdapter;

    private SearchResult currentResult;

    private LinearLayout progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        progressBar = findViewById(R.id.progress_circular);
        nextPage = findViewById(R.id.next_page);
        previousPage = findViewById(R.id.previous_page);

        imageListsAdapter = new ImageListsAdapter(new ArrayList<Item>(), getApplicationContext());
        imagesList = findViewById(R.id.images_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        imagesList.setLayoutManager(layoutManager);
        imagesList.setNestedScrollingEnabled(true);

        imagesList.setAdapter(imageListsAdapter);


        httpClientFacade = HttpClientFacade.getInstance();

        final Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");

        final Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(Constants.QUERY_KEY, keyword);
        queryParameters.put(Constants.SEARCH_ENGINE_ID_KEY, Constants.SEARCH_ENGINE_ID_VALUE);
        queryParameters.put(Constants.API_KEY, Constants.API_VALUE);

        getSupportActionBar().setTitle("Search - " + keyword);

        new LoadImages().execute(queryParameters);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    class LoadImages extends AsyncTask<Map<String, String>, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Map<String, String>... queryParameters) {

            try {
                final String result = httpClientFacade.makeGetRequest(Constants.URL_ENDPOINT, queryParameters[0]);
                Log.d(TAG, "Result: " + result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            parseAndShowResults(s);
        }
    }

    public void parseAndShowResults(final String result) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SearchResult searchResult = null;
        try {
            searchResult = objectMapper.readValue(result, SearchResult.class);

            this.currentResult = searchResult;

            imageListsAdapter.add(searchResult.getItems());
            imageListsAdapter.notifyDataSetChanged();

            if(searchResult.getQueries().getNextPage() != null) {
                nextPage.setVisibility(View.VISIBLE);
            }
            else {
                nextPage.setVisibility(View.GONE);
            }

            if(searchResult.getQueries().getPreviousPage() != null) {
                previousPage.setVisibility(View.VISIBLE);
            }
            else {
                previousPage.setVisibility(View.GONE);
            }

            imagesList.scrollToPosition(0);

            Log.d(TAG, "parseAndShowResults: " + objectMapper.writeValueAsString(searchResult.getQueries()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void goToNextPage(final View view) {
        final String nextPageStartIndex = String.valueOf(currentResult.getQueries().getNextPage().get(0).getStartIndex());

        final Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(Constants.QUERY_KEY, keyword);
        queryParameters.put(Constants.SEARCH_ENGINE_ID_KEY, Constants.SEARCH_ENGINE_ID_VALUE);
        queryParameters.put(Constants.API_KEY, Constants.API_VALUE);
        queryParameters.put(Constants.START_INDEX_KEY, nextPageStartIndex);

        new LoadImages().execute(queryParameters);

    }

    public void goToPreviousPage(final View view) {
        final String previousPageStartIndex = String.valueOf(currentResult.getQueries().getPreviousPage().get(0).getStartIndex());

        final Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(Constants.QUERY_KEY, keyword);
        queryParameters.put(Constants.SEARCH_ENGINE_ID_KEY, Constants.SEARCH_ENGINE_ID_VALUE);
        queryParameters.put(Constants.API_KEY, Constants.API_VALUE);
        queryParameters.put(Constants.START_INDEX_KEY, previousPageStartIndex);

        new LoadImages().execute(queryParameters);

    }

}
