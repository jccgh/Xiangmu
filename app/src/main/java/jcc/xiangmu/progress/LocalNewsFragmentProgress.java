package jcc.xiangmu.progress;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import jcc.xiangmu.module.been.NewsBeen;
import jcc.xiangmu.module.local.ILocalload;
import jcc.xiangmu.module.local.LocalloadImpl;
import jcc.xiangmu.view.fragment.ILocalNewsFragement;

/**
 * Created by Administrator on 2016/10/31.
 */

public class LocalNewsFragmentProgress {
    private ILocalload iLocalload;
    private ILocalNewsFragement iLocalNewsFragement;
    private Context context;
    private String channelid;

    public LocalNewsFragmentProgress(Context context,String channelid,ILocalNewsFragement iLocalNewsFragement) {
        this.iLocalNewsFragement = iLocalNewsFragement;
        this.context = context;
        this.channelid = channelid;
        this.iLocalload = new LocalloadImpl();
    }
    public void localstart() throws IOException {
        File file = new File(context.getExternalFilesDir(null)+ File.separator+channelid+".txt");
        FileInputStream input = null;
        StringBuffer bf = new StringBuffer();
//        //
//        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//        BufferedReader reader = new BufferedReader(new FileReader(file));
//        reader.read
        //
        try {
             input = new FileInputStream(file);
            byte[] bt = new byte[1024];
            int len;
            while((len=input.read(bt))!=-1){
                bf.append(new String(bt,0,len));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(input!=null){
                input.close();
            }
        }

        List<NewsBeen> locallist =  iLocalload.getList(bf.toString());
        iLocalNewsFragement.localUpdateView(locallist);
    }
}
