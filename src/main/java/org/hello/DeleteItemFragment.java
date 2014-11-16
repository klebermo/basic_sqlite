package org.hello;

import android.app.Activity;
import android.os.Bundle;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DeleteItemFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.confirm_delete)
               .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       ((FormEditItemActivity)getActivity()).positiveClick();
                   }
               })
               .setNegativeButton(R.string.not, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       ((FormEditItemActivity)getActivity()).negativeClick();
                   }
               });

        return builder.create();
    }
}
