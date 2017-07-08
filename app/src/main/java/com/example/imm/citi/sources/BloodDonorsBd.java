package com.example.imm.citi.sources;

import android.app.Activity;
import android.support.v4.util.Pair;


import com.example.imm.citi.agents.Agent;
import com.example.imm.citi.agents.RemoteAgent;
import com.example.imm.citi.technicalClasses.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BloodDonorsBd extends Source{
    final String url = "www.blooddonorsbd.com", urlPre = "http://", urlPost = "//group/";

    public BloodDonorsBd(Activity act, Service service) {
        super(act, service);
    }

    protected String getUrl() {
        return url;
    }


    public ArrayList<Agent> fetchResult(ArrayList<Pair<String, String>> chosenOptions) throws Exception{
        BloodDonationFilter bldFil = new BloodDonationFilter();
        generateCodes(chosenOptions, bldFil);
        System.out.println(chosenOptions + " ______________________ " + bldFil.bGroup + " , " + bldFil.city);


        showProgress();

        URL url = new URL(urlPre + this.url + urlPost + bldFil.bGroup);
        Map<String,Object> params = new LinkedHashMap<>();

        params.put("living_district", bldFil.city);
        params.put("home_district", bldFil.city);
        params.put("country", "20");
        params.put("status", "1");
        params.put("filterit", "Filter");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));


        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;)
            sb.append((char)c);
        String response = sb.toString();

        agentList = parse(agentList, response);

        loading.dismiss();
        return agentList;
    }

    private ArrayList<Agent> parse(ArrayList<Agent> agents, String response)
    {
        Pattern pattern1 = Pattern.compile("<div(.)class=(.)item_row1(.)><div(.)class=(.)item1(.)>" +
                "(.*?)<(.)div><div(.)class=(.)item3(.)>(.*?)<(.)div><div(.)class=(.)item6(.)>" +
                "(.*?)<(.)div><div(.)class=(.)item4(.)>(.*?)<(.)div><div(.)class=(.)item4m(.)>" +
                "(.*?)<(.)div><div(.)class=(.)item5(.)><img(.)src=(.)(.*?)(..)width=(.)15(..)" +
                "border=(.)0(....)>(.)<img(.)src=(.)(.*?)(..)width=(.)15(..)border=(.)0(...)>" +
                "(.)<img(.)src=(.)(.*?)(..)width=(.)90(..)border=(.)0(....)><(.)div><div(.)" +
                "class=(.)item2(.)><a(.)href=(.)(.*?)(.)>Send<(.)a><(.)div>" +
                "<div(.)class=(.)item2p(.)><a(.)href=(.)(.*?)(..)>View<(.)a><(.)div><(.)div>");

        Matcher matcher1 = pattern1.matcher(response);
        int i=0;
        while (matcher1.find()) {
            String name = matcher1.group(7);
            String address = matcher1.group(22);
            String phone = matcher1.group(27);

            String detailsLink = matcher1.group(73);

            if(name.equals("")) name = "Blood Donor #";


            RemoteAgent temp = new RemoteAgent("Blood Donation");
            temp.setAttr(name, phone, detailsLink, address, "");
            agents.add(temp);
//			System.out.println("-------");
//			System.out.println(name+" \n "+group + " \n " + address + " \n " + phone + " \n " + detailsLink);
//			System.out.println("-------");
            i++;
        }
        return agents;
    }
}


