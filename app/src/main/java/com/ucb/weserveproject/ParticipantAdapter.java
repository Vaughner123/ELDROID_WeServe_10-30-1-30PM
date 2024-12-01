package com.ucb.weserveproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ucb.weserveproject.R;

import java.util.List;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder> {
    private Context context;
    private List<Participant> participantList;

    public ParticipantAdapter(Context context, List<Participant> participantList) {
        this.context = context;
        this.participantList = participantList;
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.participant_item, parent, false);
        return new ParticipantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
        Participant participant = participantList.get(position);
        holder.participantName.setText(participant.getFirstName() + " " + participant.getLastName());

        // Add any click listeners if needed
        holder.checkIcon.setOnClickListener(v -> {
            // Handle check icon click
        });

        holder.closeIcon.setOnClickListener(v -> {
            // Handle close icon click
        });
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }

    public static class ParticipantViewHolder extends RecyclerView.ViewHolder {
        RadioButton radioButton;
        TextView participantName;
        ImageView checkIcon, closeIcon;

        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioButton);
            participantName = itemView.findViewById(R.id.tv_participant_name);
            checkIcon = itemView.findViewById(R.id.check_icon);
            closeIcon = itemView.findViewById(R.id.close_icon);
        }
    }
}
