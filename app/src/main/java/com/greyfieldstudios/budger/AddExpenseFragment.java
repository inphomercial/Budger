package com.greyfieldstudios.budger;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AddExpenseFragment extends Fragment {

    private TextView tvDialogExpense;
    private TextView tvDialogDesc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate view we want used
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        // Grab all values needed
        tvDialogExpense = (TextView) view.findViewById(R.id.dialog_expense);
        tvDialogDesc = (TextView) view.findViewById(R.id.dialog_desc);

        // Used to remove the fragment in place of a backpress
        //getActivity().onBackPressed()

        // Return view
        return view;
    }

    // INITIALIZE EVERYTHING ON onCreateView
    // DO everything on the onResume()

    // my way of getting access
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
