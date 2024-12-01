package com.ucb.weserveproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class ParticipantFragment extends Fragment {
    private RecyclerView recyclerView;
    private ParticipantAdapter adapter;
    private List<Participant> participantList = new ArrayList<>();
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.participants_layout, container, false);

        recyclerView = view.findViewById(R.id.participant_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ParticipantAdapter(getContext(), participantList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        fetchParticipants();

        return view;
    }

    private void fetchParticipants() {
        String eventId = "exampleEventId";
        db.collection("events").document(eventId).collection("participants")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (var document : querySnapshot) {
                        String firstName = document.getString("firstName");
                        String lastName = document.getString("lastName");
                        participantList.add(new Participant(firstName, lastName));
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                });
    }
}
