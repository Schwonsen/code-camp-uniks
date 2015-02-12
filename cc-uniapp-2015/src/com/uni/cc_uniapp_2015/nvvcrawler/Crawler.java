package com.uni.cc_uniapp_2015.nvvcrawler;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maxim on 10.02.2015.
 */
public abstract class Crawler extends AsyncTask<Object, Object, CrawlValue>
{
    protected Crawls listener;

    public Crawler(Crawls listener)
    {
        this.listener = listener;
    }

    public void fetch()
    {
        Object params = null;
        this.execute(params);
    }

    public void fetch(Object[] params)
    {
        this.execute(params);
    }

    protected CrawlValue doInBackground(Object... params)
    {
        CrawlValue crv = new CrawlValue();
        return this.backgoundCrawl(params, crv);
    }

    protected abstract CrawlValue backgoundCrawl(Object[] params,
                                                 CrawlValue crv);

    protected void onPostExecute(CrawlValue result)
    {
        listener.onCrawlFinish(result);
    }

    protected Matcher getUrlMatcher(String URL, String RegEx)
    {
        String html = this.getHtml(URL);
        Pattern pat = Pattern.compile(RegEx);
        return pat.matcher(html);
    }

    protected Matcher getStringMatcher(String string, String RegEx)
    {
        Pattern pat = Pattern.compile(RegEx);
        return pat.matcher(string);
    }

    private String getHtml(String urlString)
    {
        StringBuilder answer = new StringBuilder();

        try
        {
            URL website = new URL(urlString);

            URLConnection connection = website.openConnection();
            InputStream is = connection.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                answer.append(inputLine);

            in.close();
        } catch (Exception e)
        {
            System.out.println("Error: Crawler don´t get html:");
            e.printStackTrace();
        }
        return answer.toString();
    }

    protected String removeTagsEntitys(String string)
    {
        string = string.replaceAll("<(.*?)>", " ")
                .replaceAll("&#252;", "" + (char) 252)
                .replaceAll("&#223;", "" + (char) 223)
                .replaceAll("&#228;", "" + (char) 228)
                .replaceAll("&#246;", "" + (char) 246);

        return string.replaceAll("&(.?);", " ");
    }
}