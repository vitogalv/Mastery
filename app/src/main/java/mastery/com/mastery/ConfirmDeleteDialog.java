package mastery.com.mastery;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

public class ConfirmDeleteDialog extends DialogFragment {

    private ConfirmDeleteDialogListener listener;

    public interface ConfirmDeleteDialogListener {
        void onPositiveClick(android.support.v4.app.DialogFragment dialog);
        void onNegativeClick(android.support.v4.app.DialogFragment dialog);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            listener = (ConfirmDeleteDialog.ConfirmDeleteDialogListener)context;
        }catch(ClassCastException e){
            Log.e("ConfirmDeleteDialog", "Activity must implement ConfirmDeleteDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.delete_skill).setMessage(R.string.delete_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onPositiveClick(ConfirmDeleteDialog.this);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onNegativeClick(ConfirmDeleteDialog.this);
                    }
                });
        return builder.create();
    }
}
