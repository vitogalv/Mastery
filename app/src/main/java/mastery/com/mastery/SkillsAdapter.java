package mastery.com.mastery;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import mastery.com.mastery.persist.Skill;

public class SkillsAdapter extends Adapter<SkillsAdapter.SkillViewHolder> {

    private static final String SKILL_NAME = "skill_name";
    private List<Skill> mySkills;
    private LayoutInflater inflater;
    private Context context;

    public SkillsAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.skill_layout, parent, false);
        return new SkillViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        Skill skill = mySkills.get(position);
        holder.title.setText(skill.title);
        holder.level.setText(String.valueOf(skill.level));
        holder.progressBar.setProgress(skill.getProgress());
    }

    @Override
    public int getItemCount() {
        if(mySkills == null){
            return 0;
        }else{
            return mySkills.size();
        }
    }

    public void setSkills(List<Skill> skills){
        mySkills = skills;
        notifyDataSetChanged();
    }


    public void deleteSkill(String title){
        for(int i = 0; i < mySkills.size(); i++){
            Skill s = mySkills.get(i);
            if(title.equals(s.title)){
                mySkills.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }

    }

    public class SkillViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView level;
        ProgressBar progressBar;

        public SkillViewHolder(View itemView, SkillsAdapter adapter) {
            super(itemView);
            title = itemView.findViewById(R.id.skill_title);
            level = itemView.findViewById(R.id.level);
            progressBar = itemView.findViewById(R.id.level_progress);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, SkillHub.class);
            intent.putExtra(SKILL_NAME, title.getText().toString());
            context.startActivity(intent);
        }
    }
}
