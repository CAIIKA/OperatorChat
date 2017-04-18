package ru.set66.operatorchat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ru.set66.operatorchat.R;
import ru.set66.operatorchat.model.Message;
import ru.set66.operatorchat.model.Person;

/**
 * Created by Alex on 17.04.2017.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {
    Context context;
    List<Message> messageList;

    public ConversationAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (message.getType().equals(1)) {//vhodyschii
            holder.lSend.setVisibility(View.GONE);
            holder.lInbox.setVisibility(View.VISIBLE);
            holder.bodyL.setText(message.getBody());
            holder.dateL.setText(message.getDate());

        } else {
            holder.lSend.setVisibility(View.VISIBLE);
            holder.lInbox.setVisibility(View.GONE);
            holder.bodyR.setText(message.getBody());
            holder.dateR.setText(message.getDate());
        }
    }

    @Override
    public int getItemCount() {
        if (messageList == null)
            return 0;
        return messageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView bodyL;
        TextView dateL;
        TextView bodyR;
        TextView dateR;
        LinearLayout lSend, lInbox;

        public ViewHolder(View itemView) {
            super(itemView);
            lSend=(LinearLayout) itemView.findViewById(R.id.lSend);
            lInbox=(LinearLayout) itemView.findViewById(R.id.lInbox);
            bodyL = (TextView) itemView.findViewById(R.id.tvMessageL);
            dateL = (TextView) itemView.findViewById(R.id.tvDateL);
            bodyR = (TextView) itemView.findViewById(R.id.tvMessageR);
            dateR = (TextView) itemView.findViewById(R.id.tvDateR);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.setHeaderTitle(R.string.context_menu_header);
            menu.add(0, 2, 0, R.string.context_menu_call);//groupId, itemId, order, title
            menu.add(0, 1, 0, R.string.context_menu_copy);
        }
    }
}
