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

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate view we want used
        view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        // Grab all values needed
        etAmount = (EditText) view.findViewById(R.id.etAmount);
        etDesc = (EditText) view.findViewById(R.id.etDesc);
        btnSubmit = (Button) view.findViewById(R.id.btn_add_expense);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpense();
            }
        });

        // Used to remove the fragment in place of a backpress
       // getActivity().onBackPressed();

        //addExpense();

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
        //expense.setDate(selected_date);
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

        /*// Modify spendable remaining
        tvSpendable = (TextView) findViewById(com.greyfieldstudios.budger.R.id.spendable_value);
        BigDecimal tvSpendableValue = new BigDecimal(tvSpendable.getText().toString());
        tvSpendableValue.subtract(expense_amount);
        tvSpendable.setText(tvSpendableValue.toPlainString());

        // Clear Expense fields
        expenseAmount.setText("");
        expenseDesc.setText("");

        // Remove focus from fields
        layout.requestFocus();

        // Hide keyboard
        hideKeyboard();*/
    }

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
