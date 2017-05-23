/*
 * Open Trivia QuizApp is a Udacity EU-Scholarship Project
 * written by Thorsten Itter, Copyright (c) 2017.
 * This Software may be used solely for non-profit educational purposes
 * unless specified otherwise by the original author Thorsten Itter
 * Questions and answers provided by Open Trivia Database
 * through a free for commercial use API maintained by PIXELTAIL GAME
 * This source code including this header may not be modified
 *
 */

package android.example.com.quizapp;

import android.content.Intent;

import com.itternet.models.QuizCategory;

import android.example.com.quizapp.listview.QuizCategoryAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Loads JSON data from assets "manually" as opposed to using retrofit.
 * Populates ListView by using my custom Array Adapter with JSON data
 * Yes we can
 */

public class CategorySelectionActivity extends AppCompatActivity {
    private static final String CATEGORIES_JSON = "categories.json";
    private static final String JSON_ENCODING = "UTF-8";
    private QuizCategory selectedCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        ArrayList<QuizCategory> qc = loadCategoriesJSON();

        QuizCategoryAdapter qca = new QuizCategoryAdapter
                (
                        CategorySelectionActivity.this,
                        R.layout.template_category_list_item, qc
                );

        ListView LvCatNames = (ListView) findViewById(R.id.category_ListView);
        LvCatNames.setAdapter(qca);

        LvCatNames.setOnItemClickListener
                (
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                selectedCategory = (QuizCategory) parent.getItemAtPosition(position);

                                Intent gotoMain = new Intent(CategorySelectionActivity.this, OptionsActivity.class);
                                gotoMain.putExtra("categoryID", selectedCategory.getCategoryID());
                                String categoryName = selectedCategory.getMainCategory();
                                if (selectedCategory.getSubCategory() != null &&
                                        !selectedCategory.getSubCategory().trim().isEmpty())
                                    categoryName += ": " + selectedCategory.getSubCategory();
                                gotoMain.putExtra("categoryName", categoryName);
                                startActivity(gotoMain);
                                finish();
                            }
                        }
                );
    }

    private ArrayList<QuizCategory> loadCategoriesJSON() {
        ArrayList<QuizCategory> categoriesJSON = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getAssets().open(CATEGORIES_JSON);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, JSON_ENCODING);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("categories");

            for (int i = 0; i < jsonArray.length(); ++i) {
                obj = jsonArray.getJSONObject(i);
                QuizCategory qc = new QuizCategory();
                qc.setCategoryID(obj.getInt("id"));
                qc.setMainCategory(obj.getString("name"));
                qc.setImage(getResources().getIdentifier(obj.getString("img"),
                        "drawable", getPackageName()));

                categoriesJSON.add(qc);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categoriesJSON;
    }
}
