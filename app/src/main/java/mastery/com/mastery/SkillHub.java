package mastery.com.mastery;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import mastery.com.mastery.persist.Skill;
import mastery.com.mastery.persist.SkillViewModel;

public class SkillHub extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener {

    private final static String SKILL_NAME = "skill_name";
    private SkillViewModel skillModel;
    private TextView rankText;
    private TextView levelText;
    private TextView hoursText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_hub);

        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        rankText = (TextView) findViewById(R.id.rank);
        levelText = (TextView) findViewById(R.id.level);
        hoursText = (TextView) findViewById(R.id.hours);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Intent intent = getIntent();
        String skillName = intent.getStringExtra(SKILL_NAME);
        setTitle(skillName);

        skillModel = ViewModelProviders.of(this).get(SkillViewModel.class);
        skillModel.setSkill(skillName);
        skillModel.getSkill().observe(this, new Observer<Skill>() {
            @Override
            public void onChanged(@Nullable Skill skill) {
                if(skill != null) {
                    rankText.setText(skill.getRank());
                    levelText.setText(String.valueOf(skill.level));
                    hoursText.setText(String.valueOf(skill.getHours()));
                    progressBar.setProgress(skill.getProgress());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_skill_hub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete) {
            ConfirmDeleteDialog deleteDialog = new ConfirmDeleteDialog();
            deleteDialog.show(getSupportFragmentManager(), "delete_skill");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPositiveClick(DialogFragment dialog) {
        Skill skill  = skillModel.getSkill().getValue();
        if(skill != null) {
            String title = skill.title;
            skillModel.deleteSkill(title);
            Intent intent = new Intent(this, MySkills.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();
    }
}
