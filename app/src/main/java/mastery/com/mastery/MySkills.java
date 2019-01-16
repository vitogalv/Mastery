package mastery.com.mastery;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import mastery.com.mastery.persist.Skill;
import mastery.com.mastery.persist.SkillsViewModel;

public class MySkills extends AppCompatActivity implements NewSkillDialog.NewSkillDialogListener {

    RecyclerView skillsRecycler;
    SkillsAdapter skillsAdapter;
    SkillsViewModel skillsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_skills);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewSkillDialog newSkillD = new NewSkillDialog();
                newSkillD.show(getSupportFragmentManager(), "new_skill");
            }
        });

        skillsRecycler = (RecyclerView)findViewById(R.id.recyclerView);
        skillsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        skillsAdapter = new SkillsAdapter(getApplicationContext());
        skillsRecycler.setAdapter(skillsAdapter);

        skillsModel = ViewModelProviders.of(this).get(SkillsViewModel.class);
        skillsModel.getSkills().observe(this, new Observer<List<Skill>>() {
                    @Override
                    public void onChanged(@Nullable List<Skill> skills) {
                        skillsAdapter.setSkills(skills);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_skills, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info){
        super.onCreateContextMenu(menu, v, info);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context_skill, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.delete:
                String title = ((TextView)info.targetView.findViewById(R.id.skill_title)).toString();
                skillsModel.deleteSkill(title);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onPositiveClick(DialogFragment dialog) {
        NewSkillDialog mDialog = (NewSkillDialog)dialog;
        String skillTitle = mDialog.getSkillTitle();
        Skill skill = new Skill();
        skill.title = skillTitle;
        skill.level = 1;
        skill.minutes = 0;
        skill.lastCommit = System.currentTimeMillis();
        skillsModel.addSkill(skill);
    }

    @Override
    public void onNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();
    }
}
