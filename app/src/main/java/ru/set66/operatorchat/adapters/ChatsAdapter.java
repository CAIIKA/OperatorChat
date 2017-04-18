package ru.set66.operatorchat.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.set66.operatorchat.Conversation;
import ru.set66.operatorchat.model.Person;
import ru.set66.operatorchat.R;

/**
 * Created by Alex on 17.04.2017.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder>{
    private static final String NAME="name";
    private static final String PHONE="phone";
    private static final String UDID="udid";
    private static final String COUNT_MESSAGES="count";
    private List<Person> chats;
    private Context context;
    private RecyclerView mRecyclerView;
    public ChatsAdapter(Context cont,List<Person> chats){
        this.chats=chats;
        this.context=cont;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chats_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person chat = chats.get(position);
        holder.name.setText(chat.getmName());
        holder.phone.setText(chat.getmPhoneNumber());
        holder.count.setText(chat.getmCountMessages());
    }

    @Override
    public int getItemCount() {
        if (chats == null)
            return 0;
        return chats.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView phone;
        TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvName);
            phone = (TextView) itemView.findViewById(R.id.tvNumber);
            count= (TextView) itemView.findViewById(R.id.tvCount);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    int pos=getPosition();//mRecyclerView.getChildAdapterPosition(v);
                    Intent intent = new Intent(context, Conversation.class);
                    intent.putExtra(UDID, chats.get(pos).getmUDID());
                    intent.putExtra(COUNT_MESSAGES, chats.get(pos).getmCountMessages());
                    intent.putExtra(PHONE, chats.get(pos).getmPhoneNumber());
                    intent.putExtra(NAME, chats.get(pos).getmName());
                    context.startActivity(intent);
                }
            });
        }
    }
}
