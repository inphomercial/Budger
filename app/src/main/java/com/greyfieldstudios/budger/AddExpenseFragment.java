package com.greyfieldstudios.budger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.greyfieldstudios.budger.Models.Expenses;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.math.BigDecimal;

public class AddExpenseFragment extends Fragment {

    private EditText etAmount;
    private EditText etDesc;

    private Button btnSubmit;
    private Button btnCancel;

    private View view;

    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    /*public static AddExpenseFragment newInstance(Calendar selected_date) {
        AddExpenseFragment fragmentDemo = new AddExpenseFragment();
        Bundle args = new Bundle();
        args.put("selected_date", selected_date);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
*/
    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    // This event fires 3rd, and is the first time views are available in the fragment
    // The onCreateView method is called when Fragment should create its View object hierarchy.
    // Use onCreateView to get a handle to views as soon as they are freshly inflated
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate view we want used
        view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        // Grab all values needed
        etAmount = (EditText) view.findViewById(R.id.etAmount);
        etDesc = (EditText) view.findViewById(R.id.etDesc);
        btnSubmit = (Button) view.findViewById(R.id.btn_add_expense);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpense();
                getActivity().onBackPressed();
            }
        });

        // Return view
        return view;
    }

    // INITIALIZE EVERYTHING ON onCreateView
    // DO everything on the onResume()

    private void addExpense() {

        // Start the progress dialog
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Adding expense");
        dialog.show();

        if(TextUtils.isEmpty(etAmount.getText())) {
            dialog.dismiss();
            Toast.makeText(getActivity(), "Set an amount!", Toast.LENGTH_LONG).show();
            return;
        }

        // Set desc to empty string if it's empty
        if(TextUtils.isEmpty(etDesc.getText())) {
            etDesc.setText("");
        }

        // Convert Amount to a BigDecimal
        String ex = etAmount.getText().toString();
        BigDecimal expense_amount = new BigDecimal(ex);

        // Add a new Expense object
        Expenses expense = new Expenses();
        expense.setAmount(expense_amount);
        expense.setDate(Application.selected_date);
        expense.setDesc(etDesc.getText().toString());
        expense.setUser(ParseUser.getCurrentUser());

        // Set object Permissions
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        expense.setACL(acl);

        // Save object in background
        expense.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {

                if(e == null) {
                    dialog.dismiss();
                } else {
                    Log.d("App", e.getMessage());
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
