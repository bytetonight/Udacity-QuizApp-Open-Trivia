package android.example.com.quizapp.listview;

import android.content.Context;
import android.example.com.quizapp.R;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by dns on 12.03.2017.
 */

public class QuizCategoryAdapter extends ArrayAdapter
{
    private List<QuizCategory> qcList;
    private int resource;
    private LayoutInflater inflater;

    public QuizCategoryAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects)
    {
        super(context, resource, objects);

        Collections.sort(objects, new Comparator<QuizCategory>() {
            @Override
            public int compare(QuizCategory lhs, QuizCategory rhs) {
                return lhs.getMainCategory().compareTo(rhs.getMainCategory());
            }
        });

        qcList = objects;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.template_category_list_item, null);

        ImageView categoryImage;
        TextView categoryMain;
        TextView categorySub;

        categoryImage = (ImageView) convertView.findViewById(R.id.ivImage);
        categoryMain = (TextView) convertView.findViewById(R.id.tvMainCategory);
        categorySub = (TextView) convertView.findViewById(R.id.tvSubCategory);

        categoryMain.setText(qcList.get(position).getMainCategory());
        categorySub.setText(qcList.get(position).getSubCategory());
        //categoryImage.set
        return convertView;
    }


}


