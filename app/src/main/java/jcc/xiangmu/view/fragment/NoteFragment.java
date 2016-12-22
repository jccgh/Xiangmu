package jcc.xiangmu.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import jcc.mycutomedittext.MyCustomEditText;
import jcc.xiangmu.R;
import jcc.xiangmu.module.user.MyUser;
import jcc.xiangmu.module.user.Note;

/**
 * Created by Administrator on 2016/11/2.
 */

public class NoteFragment extends Fragment {
    private EditText met;
    private MyCustomEditText mcustomet;
    private String keyword;
    private Button mbt;
    private String title;
    private String content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notefragmentlayout, container, false);
        keyword = getArguments().getString("keyword");
        title = getArguments().getString("title");
        content = getArguments().getString("content");
        initview(view);
        initevent();

        return view;
    }

    private void initevent() {
        if(keyword!=null){
            met.setText("标题");
        }else{
            met.setText(title);
            mcustomet.setText(content);
        }
        mbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = met.getText().toString();
                String content = mcustomet.getText().toString();
                if (title != null && content != null && !title.equals("") && !content.equals("")) {
                    save(title, content);
                }else{
                    Toast.makeText(getActivity(), "没有数据可以保存", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void save(String title, String content) {
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        if(myUser==null){

            Toast.makeText(getActivity(), "请登录后再执行此操作", Toast.LENGTH_SHORT).show();

            return;
        }
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(myUser);
        note.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getActivity(), "保存失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initview(View view) {
        met = (EditText) view.findViewById(R.id.notetitle_et);
        mcustomet = (MyCustomEditText) view.findViewById(R.id.note_mycustomedittext);
        mbt = (Button) view.findViewById(R.id.note_save_bt);
    }
}
