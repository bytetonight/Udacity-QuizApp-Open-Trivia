package android.example.com.quizapp.listview;

/**
 * Created by dns on 12.03.2017.
 */

public class RowItem
{
    private String itemName;
    private int itemImage;

    public RowItem(String itemName, int itemImage)
    {
        this.itemName = itemName;
        this.itemImage = itemImage;
    }

    //region Property Getters & Setters
    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public int getItemImage()
    {
        return itemImage;
    }

    public void setItemImage(int itemImage)
    {
        this.itemImage = itemImage;
    }
    //endregion
}
