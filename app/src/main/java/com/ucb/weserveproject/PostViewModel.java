package com.ucb.weserveproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PostViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<DashboardFragment.PostData>> postDataList = new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<ArrayList<DashboardFragment.PostData>> historyDataList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<ArrayList<DashboardFragment.PostData>> getPostDataList() {
        return postDataList;
    }

    public LiveData<ArrayList<DashboardFragment.PostData>> getHistoryDataList() {
        return historyDataList;
    }

    public void addPost(DashboardFragment.PostData postData) {
        ArrayList<DashboardFragment.PostData> currentList = postDataList.getValue();
        if (currentList != null) {
            currentList.add(postData);
            postDataList.setValue(currentList); // Update the list
        }
    }

    public void addToHistory(DashboardFragment.PostData postData) {
        ArrayList<DashboardFragment.PostData> currentList = historyDataList.getValue();
        if (currentList != null) {
            currentList.add(postData);
            historyDataList.setValue(currentList); // Update the list
        }
    }
}
