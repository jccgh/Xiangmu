package jcc.xiangmu.view.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import jcc.mycustomtopview.MyCustomTopView;
import jcc.mycustomtopview.OnClickListener;
import jcc.xiangmu.R;
import jcc.xiangmu.module.been.NoteBeen;
import jcc.xiangmu.module.user.MyUser;
import jcc.xiangmu.module.user.Note;
import jcc.xiangmu.view.fragment.NoteFragment;

public class NoteActivity extends AppCompatActivity {

    private MyCustomTopView mtop;
    private Button madd;
    private ListView mlv;
    private RelativeLayout mrightlayout;
    private Button mquery_bt;
    private Map<String, String> map;
    private List<String>titlelist;
    private List<NoteBeen> mlist;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initview();
        initevent();
    }

    private void initevent() {
        mtop.setOnClickListener(new OnClickListener() {
            @Override
            public void OnLeftClick() {
                NoteActivity.this.finish();
            }

            @Override
            public void OncenterClick() {

            }

            @Override
            public void OnRightClick() {

            }
        });
        madd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NoteFragment noteFragment = new NoteFragment();
                Bundle bundle = new Bundle();
                bundle.putString("keyword","添加");
                noteFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.note_rightlayout,noteFragment);
                transaction.commit();
            }
        });
        mquery_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titlelist = new ArrayList<String>();
                query();
                adapter = new ArrayAdapter(NoteActivity.this,android.R.layout.simple_list_item_1,titlelist);
                mlv.setAdapter(adapter);
            }
        });
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteFragment noteFragment = new NoteFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title",mlist.get(position).getTitle());
                bundle.putString("content",mlist.get(position).getContent());
                noteFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.note_rightlayout,noteFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void query() {
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        if(myUser==null){
            Toast.makeText(NoteActivity.this,"请登录在执行此操作",Toast.LENGTH_SHORT).show();
            return;
        }
        BmobQuery<Note>query = new BmobQuery<>();
        query.addWhereEqualTo("user",myUser);
        query.order("-updatedAt");
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if(e==null){
                    if(list.size()==0){
                        Toast.makeText(NoteActivity.this,"没有数据",Toast.LENGTH_SHORT).show();

                    }else{
                        mlist = new ArrayList<NoteBeen>();
                        for(Note note:list){
                            String title = note.getTitle();
                            String content = note.getContent();
                            mlist.add(new NoteBeen(title,content));
                        }
                        for(NoteBeen been:mlist){
                            titlelist.add(been.getTitle());
                        }
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(NoteActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initview() {
        mtop = (MyCustomTopView)findViewById(R.id.note_TopView);
        madd = (Button)findViewById(R.id.note_addbt);
        mlv = (ListView)findViewById(R.id.note_listview);
        mrightlayout = (RelativeLayout)findViewById(R.id.note_rightlayout);
        mquery_bt = (Button)findViewById(R.id.note_query);
    }
}
