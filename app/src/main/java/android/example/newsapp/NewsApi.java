package android.example.newsapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsApi {

    private static final String REQUEST_URL = "http://content.guardianapis.com/search";
    private static final String APK_KEY = "test";

    public List<News> fetchNewsApp(String topic) throws Exception {
        Uri base = Uri.parse(REQUEST_URL);

        Uri.Builder uriBuilder = base.buildUpon();

        uriBuilder.appendQueryParameter("topic", topic);
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("api-key", APK_KEY);

        String jsonResponse = HttpClient.makeRequest(uriBuilder.build().toString(), "GET");
        return convertStringToJson(jsonResponse);
    }

    public ArrayList<News> convertStringToJson(String newsJson) {
        ArrayList<News> arrayList = null;
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJson);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray jsonArray = response.getJSONArray("results");

            if(jsonArray.length() > 0){
                arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String sectionName = jsonObject.getString("sectionName");
                    String title = jsonObject.getString("webTitle");
                    String webUrl = jsonObject.getString("webUrl");
                    String webPublicationDate = jsonObject.optString("webPublicationDate");

                    String[] dateTime = webPublicationDate.split("T");
                    String date = dateTime[0];
                    String time = dateTime[1];
                    time = time.substring(0, 5);
                    String author = "";
                    JSONArray tags = jsonObject.getJSONArray("tags");
                    if (tags != null) {
                        for (int k = 0; k < tags.length(); k++) {
                            JSONObject tagsObject = tags.getJSONObject(k);
                            author = tagsObject.getString("webTitle");
                        }
                    }
                    arrayList.add(new News(title, webUrl, sectionName, date, time, author));
                }
            }

        } catch (JSONException e) {
            Log.i("NewsApi", "Can't parse json string :" + e.toString());
        }
        return arrayList;
    }
}
