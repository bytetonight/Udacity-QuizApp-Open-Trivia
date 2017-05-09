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
import android.widget.ImageView;
import android.widget.TextView;

import com.itternet.models.QuizCategory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * Populate the Category Selection ListView with QuizCategory Models
 */
public class QuizCategoryAdapter extends ArrayAdapter
{
    private List<QuizCategory> qcList;
    private int resource;
    private LayoutInflater inflater;


    public QuizCategoryAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects)
    {
        super(context, resource, objects);

        Collections.sort(objects, new Comparator<QuizCategory>()
        {
            @Override
            public int compare(QuizCategory lhs, QuizCategory rhs)
            {
                return lhs.getMainCategory().compareTo(rhs.getMainCategory());
            }
        });

        qcList = objects;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    //About Viewholder : see https://www.youtube.com/watch?v=OTEiRiMaQ7M
    class MyViewHolder
    {
        ImageView categoryImage;
        TextView categoryMain;
        TextView categorySub;

        public MyViewHolder(View v)
        {
            categoryImage = (ImageView) v.findViewById(R.id.ivImage);
            categoryMain = (TextView) v.findViewById(R.id.tvMainCategory);
            categorySub = (TextView) v.findViewById(R.id.tvSubCategory);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        MyViewHolder holder = null;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.template_category_list_item, parent, false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (MyViewHolder) convertView.getTag();
        }

        holder.categoryMain.setText(qcList.get(position).getMainCategory());
        holder.categorySub.setText(qcList.get(position).getSubCategory());
        holder.categoryImage.setImageResource(qcList.get(position).getImage());

        return convertView;
    }
}


