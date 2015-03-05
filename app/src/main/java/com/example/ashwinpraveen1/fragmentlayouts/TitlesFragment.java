package com.example.ashwinpraveen1.fragmentlayouts;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class TitlesFragment extends ListFragment {

    boolean mDuelPane;
    int mCurCheckPosition=0;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CursorChoice",mCurCheckPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> connectArrayToListView = new
                ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1,Superhero_info.Superhero);
        setListAdapter(connectArrayToListView);
        View details= getActivity().findViewById(R.id.details);
        mDuelPane = details!= null && details.getVisibility()==View.VISIBLE;
        if(savedInstanceState!= null) {
            mCurCheckPosition=  savedInstanceState.getInt("CursorChoice",0);
        }
        if(mDuelPane) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(mCurCheckPosition);
        }
    }

    private void showDetails(int index) {
        mCurCheckPosition=index;
        if(mDuelPane) {
            getListView().setItemChecked(index, true);
            DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details);
            if(details == null || details.getShownIndex() != index) {
                details = DetailsFragment.newInstance(index);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
            else {
                Intent intent = new Intent();
                intent.setClass(getActivity(),DetailsActivity.class);
                intent.putExtra("index", index);
                startActivity(intent);
            }
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }


}
