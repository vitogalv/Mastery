package mastery.com.mastery;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;

public class NewSkillDialog extends DialogFragment {

    private NewSkillDialogListener listener;
    private EditText skillTitle;

    public interface NewSkillDialogListener{
        void onPositiveClick(DialogFragment dialog);
        void onNegativeClick(DialogFragment dialog);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            listener = (NewSkillDialogListener)context;
        }catch(ClassCastException e){
            Log.e("NewSkillDialog", "Activity must implement NewSkillDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        skillTitle = new EditText(getContext());
        builder.setTitle(R.string.new_skill_title).setView(skillTitle).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onNegativeClick(NewSkillDialog.this);
            }
        }).setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onPositiveClick(NewSkillDialog.this);
            }
        });
        return builder.create();
    }

    public String getSkillTitle(){
        return skillTitle.getText().toString();
    }
}
