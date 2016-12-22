package jcc.xiangmu.module.parsejson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jcc.xiangmu.module.been.NewsBeen;

/**
 * Created by Administrator on 2016/10/20.
 */
public class NewsParse{
    public  List<NewsBeen> getList(String json) {
        List<NewsBeen> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body");
            JSONObject pagebean = showapi_res_body.getJSONObject("pagebean");
            JSONArray contentlist = pagebean.getJSONArray("contentlist");
            for (int i = 0; i < contentlist.length(); i++) {
                JSONObject jb = contentlist.getJSONObject(i);
                String content = jb.getString("content");
                String pubDate = jb.getString("pubDate");
                String title = jb.getString("title");
                String desc = jb.getString("desc");
                String source = jb.getString("source");
                String link = jb.getString("link");
                JSONArray imageurls = jb.getJSONArray("imageurls");
                List<String>imglist = new ArrayList<>();
                if(imageurls.length()>0){
                    for (int j = 0; j < imageurls.length(); j++) {
                        JSONObject imgobject = imageurls.getJSONObject(j);
                        String imgurl = imgobject.getString("url");
                        imglist.add(imgurl);
                    }
                }
                list.add(new NewsBeen(content,pubDate,title,imglist,desc,source,link));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}