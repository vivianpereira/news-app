package android.example.newsapp;

public class News {

    private final String mTitle;
    private final String mSectionName;
    private final String mWebURL;
    private final String mDate;
    private final String mTime;
    private final String mAuthor;


    public News(String title, String webUrl, String sectionName, String date, String time, String author) {
        mWebURL = webUrl;
        mTitle = title;
        mSectionName = sectionName;
        mDate = date;
        mTime = time;
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getWebURL() {
        return mWebURL;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public String getAuthor() {
        return mAuthor;
    }
}

