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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;

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

        String[] categories = getResources().getStringArray(R.array.categories);
        List<QuizCategory> qc = new ArrayList<>();
        JSONObject obj = null;
        String categoryName = "";
        int categoryID = -1;
        //String testJSON = "{\"catID\":\"any\",\"catName\":\"Any Category\"}";
        for (String item : categories)
        {

            try
            {
                obj = new JSONObject(item);
                categoryID = obj.getInt("catID");
                categoryName = obj.optString("catName");

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            qc.add(new QuizCategory(categoryID, categoryName));
        }
            
        QuizCategoryAdapter qca = new QuizCategoryAdapter(CategorySelectionActivity.this,
                R.layout.template_category_list_item, new ArrayList<>(qc) );

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
}
