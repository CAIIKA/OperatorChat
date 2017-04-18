package ru.set66.operatorchat;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.set66.operatorchat.adapters.ChatsAdapter;
import ru.set66.operatorchat.api.Api;
import ru.set66.operatorchat.model.Person;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Person> chats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chats = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.chats_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        ChatsAdapter adapter = new ChatsAdapter(this,chats);
        recyclerView.setAdapter(adapter);
        Api.getApi().getData("/chatters.csv").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream input=response.body().byteStream();

                chats.addAll(getString(input));
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Nullable
    private List<Person> getString(InputStream inputStream){
        int ch;
        List<Byte> byteList=new ArrayList<Byte>();

        try {
            while ((ch = inputStream.read()) != -1) {
                byteList.add((byte)ch);
            }
            byte[] bytes=new byte[byteList.size()];
            int index = 0;
            for (byte b : byteList) {
                bytes[index++] = b;
            }
            String str = new String(bytes, "cp-1251");
            return parseResponse(str);
        }catch (Exception ex){return null;}

    }
    @Nullable
    private List<Person> parseResponse(String str){
        List<Person> persons=new ArrayList<Person>();
        String[] arrStr=str.split("\r\n");
        for (String stroka:arrStr) {
            String[] temp=stroka.split(";");
            Person p=new Person(temp[0],temp[3],temp[2],temp[1]);
            persons.add(p);
        }
        return persons;
    }
}
