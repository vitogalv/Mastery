package mastery.com.mastery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.List;
import java.util.TreeMap;

import mastery.com.mastery.components.SkillView;
import mastery.com.mastery.persist.MasteryDB;
import mastery.com.mastery.persist.Skill;
import mastery.com.mastery.persist.SkillDAO;

public class MySkills extends AppCompatActivity implements NewSkillDialog.NewSkillDialogListener {

    MasteryDB db;
    TreeMap<Long, Skill> mySkills;

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
        db = MasteryDB.getInstance(getApplicationContext());
        mySkills = new TreeMap<>();
        Populator populator = new Populator();
        populator.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_skills, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPositiveClick(DialogFragment dialog) {
        NewSkillDialog mDialog = (NewSkillDialog)dialog;
        String skillTitle = mDialog.getSkillTitle();
        NewSkillCreator task = new NewSkillCreator(skillTitle);
        task.execute();
    }

    @Override
    public void onNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();
    }

    private class Populator extends AsyncTask<Void, Void, List<Skill>> {

        @Override
        protected List<Skill> doInBackground(Void... voids) {
            SkillDAO dao = db.skillDao();
            return dao.getAll();
        }

        @Override
        protected void onPostExecute(List<Skill> result){
            LinearLayout skillContainer = (LinearLayout)findViewById(R.id.skills_content);
            for(Skill s : result){
                SkillView skillView = new SkillView(getApplicationContext(), s);
                skillContainer.addView(skillView);
            }
        }
    }

    private class NewSkillCreator extends AsyncTask<Void, Void, Void>{

        private Skill skill;

        NewSkillCreator(String skillTitle){
            skill = new Skill();
            skill.title = skillTitle;
            skill.level = 1;
            skill.minutes = 0.0;
            skill.lastCommit = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SkillDAO dao = db.skillDao();
            mySkills.put(skill.lastCommit, skill);
            dao.insertSkill(skill);
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            SkillView skillView = new SkillView(getApplicationContext(), skill);
            LinearLayout skills = findViewById(R.id.skills_content);
            skills.addView(skillView, 0);
        }
    }
}
