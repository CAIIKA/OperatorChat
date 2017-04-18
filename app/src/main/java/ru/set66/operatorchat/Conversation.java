package ru.set66.operatorchat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.set66.operatorchat.adapters.ChatsAdapter;
import ru.set66.operatorchat.adapters.ConversationAdapter;
import ru.set66.operatorchat.api.Api;
import ru.set66.operatorchat.model.Message;
import ru.set66.operatorchat.model.Person;

/**
 * Created by Alex on 17.04.2017.
 */

public class Conversation extends AppCompatActivity {
    private static final String NAME="name";
    private static final String PHONE="phone";
    private static final String UDID="udid";
    private static final String COUNT_MESSAGES="count";
    int countMessages;
    String phone;

    RecyclerView recyclerView;
    List<Message> messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        Intent intent = getIntent();
        String name = intent.getStringExtra(NAME);
        countMessages=Integer.valueOf(intent.getStringExtra(COUNT_MESSAGES));
        phone=intent.getStringExtra(PHONE);
        String udid_user=intent.getStringExtra(UDID);
        messages = new ArrayList<>();
        getSupportActionBar().setTitle("\t" + name);
        getSupportActionBar().setSubtitle("\t" + phone);
        recyclerView = (RecyclerView) findViewById(R.id.conversation_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        registerForContextMenu(recyclerView) ;
        ConversationAdapter adapter = new ConversationAdapter(this,messages);
        recyclerView.setAdapter(adapter);

        Api.getApi().getData("/chat_"+udid_user+".csv").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream input=response.body().byteStream();
                messages.addAll(getString(input));
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    @Nullable
    private List<Message> getString(InputStream inputStream){
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
            return parseMessage(str);
        }catch (Exception ex){return null;}

    }
    @Nullable
    private List<Message> parseMessage(String str){
        List<Message> messages=new ArrayList<Message>();
        SimpleDateFormat dFormat=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date d;
        String[] arrStr=str.split("\r\n");
        countMessages=arrStr.length;
        for (String stroka:arrStr) {
            String[] temp=stroka.split(";");
            d = new Date(Long.valueOf(temp[0]+"000"));
            Message m=new Message(dFormat.format(d),temp[1],temp[2],temp[3],temp[4],temp[5]);
            messages.add(m);
        }
        return messages;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_call:
                String uri = "tel:" + phone;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conversation, menu);
        return true;
    }



    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Toast.makeText(this, "context", Toast.LENGTH_SHORT).show();
        int ii=item.getItemId();
        switch (item.getItemId()) {

            case 1:
                Toast.makeText(this, "Copy", Toast.LENGTH_SHORT).show();
                @SuppressWarnings("deprecation")
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(messages.get(info.position).getBody());

                break;
            case 2:
                Toast.makeText(this, "Call", Toast.LENGTH_SHORT).show();
//                deleteMessage(listConversationMessage.get(info.position).getId());
//                listConversationMessage.get(info.position).delete(ConversationActivity.this);
//                listConversationMessage.remove(info.position);
//                lv.setAdapter(new ConversationAdapter(ConversationActivity.this, listConversationMessage));
//                lv.setSelection(100500);
                break;
        }
        return true;
    }
}
