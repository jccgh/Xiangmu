package jcc.xiangmu.module.parsejson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jcc.xiangmu.module.been.ExpressBeen;

/**
 * Created by Administrator on 2016/10/15.
 */
public class Parsejson {
    public List<ExpressBeen> parse(String json){
        List<ExpressBeen>list = new ArrayList<>();
        try {
            JSONObject jb = new JSONObject(json);
            JSONObject showapi_res_body = jb.getJSONObject("showapi_res_body");
            String expTextName = showapi_res_body.getString("expTextName");
            JSONArray data = showapi_res_body.getJSONArray("data");
            if(data!=null){
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = data.getJSONObject(i);
                    String time = jsonObject.getString("time");
                    String context = jsonObject.getString("context");
                    list.add(new ExpressBeen(time,context));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
}
