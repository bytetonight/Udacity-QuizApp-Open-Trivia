package android.example.com.quizapp;

import android.content.Intent;
import android.example.com.quizapp.listview.QuizCategory;
import android.example.com.quizapp.listview.QuizCategoryAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CategorySelectionActivity extends AppCompatActivity
{
    QuizCategory selectedCategory;
    String selectedCategoryName;
    int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_selection);

        //String[] categories = getResources().getStringArray(R.array.categories);
        ArrayList<QuizCategory> qc = loadCategoriesJSON();

            
        QuizCategoryAdapter qca = new QuizCategoryAdapter(CategorySelectionActivity.this,
                R.layout.template_category_list_item, qc );

        ListView LvCatNames = (ListView) findViewById(R.id.category_ListView);
        LvCatNames.setAdapter(qca);

        LvCatNames.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        selectedIndex = position;
                        //selectedCategoryName = String.valueOf(parent.getItemAtPosition(position));
                        selectedCategory = (QuizCategory) parent.getItemAtPosition(position);
                        //String catID = String.valueOf(selectedCategory.getCategoryID());
                        Intent gotoMain = new Intent(CategorySelectionActivity.this, MainActivity.class);
                        gotoMain.putExtra("categoryID", selectedCategory.getCategoryID());
                        startActivity(gotoMain);
                        finish();
                        //Toast.makeText(CategorySelectionActivity.this, catID, Toast.LENGTH_SHORT).show();


                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //return super.onOptionsItemSelected(item);
        int id = item.getItemId();
        return true;
    }

    private ArrayList<QuizCategory> loadCategoriesJSON()
    {
        ArrayList<QuizCategory> categoriesJSON = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getAssets().open("categories.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("categories");

            for (int i = 0; i < m_jArry.length(); i++) {
                obj = m_jArry.getJSONObject(i);
                QuizCategory qc = new QuizCategory();
                qc.setCategoryID(obj.getInt("id"));
                qc.setMainCategory(obj.getString("name"));
                qc.setImage(getResources().getIdentifier(obj.getString("img"), "drawable", getPackageName()));
                                //Add your values in your `ArrayList` as below:
                categoriesJSON.add(qc);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categoriesJSON;
    }
}
