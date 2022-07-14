package android.example.newsapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient {

    public static String makeRequest(String stringUrl, String method) throws Exception {
        if (stringUrl == null || stringUrl.isEmpty()) {
            throw new Exception("URL must not be null or empty");
        } else {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) new URL(stringUrl).openConnection();
                urlConnection.setReadTimeout(20000);
                urlConnection.setConnectTimeout(30000);
                urlConnection.setRequestMethod(method);
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e("HttpClient", "Error response code: " + urlConnection.getResponseCode());
                    throw new Exception("Server return error: " + urlConnection.getResponseCode());
                }
            } finally {
                close(urlConnection, inputStream);
            }
            return jsonResponse;
        }
    }

    private static void close(HttpURLConnection urlConnection, InputStream inputStream) throws IOException {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}