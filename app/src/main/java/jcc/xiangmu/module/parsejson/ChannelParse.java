package jcc.xiangmu.module.parsejson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jcc.xiangmu.module.been.ChannelBeen;

/**
 * Created by Administrator on 2016/10/19.
 */
public class ChannelParse {

    public static List<ChannelBeen> parsejson(String json){
        List<ChannelBeen>list = new ArrayList<>();
        try {
            JSONObject jb = new JSONObject(json);
            JSONObject showapi_res_body = jb.getJSONObject("showapi_res_body");
            JSONArray channelList = showapi_res_body.getJSONArray("channelList");
            for (int i = 0; i < channelList.length(); i++) {
                JSONObject object = channelList.getJSONObject(i);
                String channelId = object.getString("channelId");
                String name = object.getString("name");
                list.add(new ChannelBeen(channelId,name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  list;
    }
}
