package android.example.com.quizapp.listview;

/**
 * Created by dns on 12.03.2017.
 */

public class QuizCategory
{
    private int categoryID;
    private int image = 0;
    private String subCategory;
    private String mainCategory;

    public QuizCategory(int categoryID, String cMain)
    {
        this.categoryID = categoryID;
        if (cMain.contains(":"))
        {
            String[] parts = cMain.split(":");
            this.mainCategory = parts[0].trim();
            this.subCategory = parts[1].trim();
        }
        else
            this.mainCategory = cMain;
    }

    public int getImage()
    {
        return image;
    }

    public void setImage(int image)
    {
        this.image = image;
    }

    public int getCategoryID()
    {
        return categoryID;
    }

    public void setCategoryID(int categoryID)
    {
        this.categoryID = categoryID;
    }

    public String getMainCategory()
    {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory)
    {
        this.mainCategory = mainCategory;
    }

    public String getSubCategory()
    {
        return subCategory;
    }

    public void setSubCategory(String subCategory)
    {
        this.subCategory = subCategory;
    }
}


/*
{"catID":"any","catName":"Any Category"}
{"catID":"9","catName":"General Knowledge"}
{"catID":"10","catName":"Entertainment: Books"}
{"catID":"11","catName":"Entertainment: Film"}
{"catID":"12","catName":"Entertainment: Music"}
{"catID":"13","catName":"Entertainment: Musicals &amp; Theatres"}
{"catID":"14","catName":"Entertainment: Television"}
{"catID":"15","catName":"Entertainment: Video Games"}
{"catID":"16","catName":"Entertainment: Board Games"}
{"catID":"17","catName":"Science &amp; Nature"}
{"catID":"18","catName":"Science: Computers"}
{"catID":"19","catName":"Science: Mathematics"}
{"catID":"20","catName":"Mythology"}
{"catID":"21","catName":"Sports"}
{"catID":"22","catName":"Geography"}
{"catID":"23","catName":"History"}
{"catID":"24","catName":"Politics"}
{"catID":"25","catName":"Art"}
{"catID":"26","catName":"Celebrities"}
{"catID":"27","catName":"Animals"}
{"catID":"28","catName":"Vehicles"}
{"catID":"29","catName":"Entertainment: Comics"}
{"catID":"30","catName":"Science: Gadgets"}
{"catID":"31","catName":"Entertainment: Japanese Anime &amp; Manga"}
{"catID":"32","catName":"Entertainment: Cartoon &amp; Animations"}*/


/*
           {\"catID\":\"any\",\"catName\":\"Any Category\"}
           {\"catID\":\"9\",\"catName\":\"General Knowledge\"}
           {\"catID\":\"10\",\"catName\":\"Entertainment: Books\"}
           {\"catID\":\"11\",\"catName\":\"Entertainment: Film\"}
           {\"catID\":\"12\",\"catName\":\"Entertainment: Music\"}
           {\"catID\":\"13\",\"catName\":\"Entertainment: Musicals &amp; Theatres\"}
           {\"catID\":\"14\",\"catName\":\"Entertainment: Television\"}
           {\"catID\":\"15\",\"catName\":\"Entertainment: Video Games\"}
           {\"catID\":\"16\",\"catName\":\"Entertainment: Board Games\"}
           {\"catID\":\"17\",\"catName\":\"Science &amp; Nature\"}
           {\"catID\":\"18\",\"catName\":\"Science: Computers\"}
           {\"catID\":\"19\",\"catName\":\"Science: Mathematics\"}
           {\"catID\":\"20\",\"catName\":\"Mythology\"}
           {\"catID\":\"21\",\"catName\":\"Sports\"}
           {\"catID\":\"22\",\"catName\":\"Geography\"}
           {\"catID\":\"23\",\"catName\":\"History\"}
           {\"catID\":\"24\",\"catName\":\"Politics\"}
           {\"catID\":\"25\",\"catName\":\"Art\"}
           {\"catID\":\"26\",\"catName\":\"Celebrities\"}
           {\"catID\":\"27\",\"catName\":\"Animals\"}
           {\"catID\":\"28\",\"catName\":\"Vehicles\"}
           {\"catID\":\"29\",\"catName\":\"Entertainment: Comics\"}
           {\"catID\":\"30\",\"catName\":\"Science: Gadgets\"}
           {\"catID\":\"31\",\"catName\":\"Entertainment: Japanese Anime &amp; Manga\"}
           {\"catID\":\"32\",\"catName\":\"Entertainment: Cartoon &amp; Animations\"}
 */