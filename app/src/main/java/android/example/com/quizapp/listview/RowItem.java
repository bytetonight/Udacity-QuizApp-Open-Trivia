package android.example.com.quizapp.listview;

/**
 * The Model of a ListView Row representing one ListView Item
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
