package mastery.com.mastery;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mastery.com.mastery.components.SkillStatsView;
import mastery.com.mastery.persist.MasteryDB;
import mastery.com.mastery.persist.Skill;
import mastery.com.mastery.persist.SkillDAO;

public class SkillHub extends AppCompatActivity {

    private Skill skill;
    SkillStatsView stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        setContentView(R.layout.activity_skill_hub);
        Intent intent = getIntent();
        int skillId = intent.getIntExtra("skill_id", 0);
        new SkillFetcher().execute(skillId);
    }

    public void deleteSkill(View view) {
        DeleteSkill task = new DeleteSkill();
        task.execute(skill);
        Intent intent = new Intent(this, MySkills.class);
    }

    private class SkillFetcher extends AsyncTask<Integer, Void, Skill>{

        @Override
        protected Skill doInBackground(Integer... ids) {
            int skillId = ids[0];
            MasteryDB db = MasteryDB.getInstance(getApplicationContext());
            SkillDAO dao = db.skillDao();
            return dao.getSkill(skillId);
        }

        @Override
        protected void onPostExecute(Skill result){
            skill = result;
        }
    }

    private class DeleteSkill extends AsyncTask<Skill, Void, Void>{

        @Override
        protected Void doInBackground(Skill... skills) {
            MasteryDB db = MasteryDB.getInstance(getApplicationContext());
            SkillDAO dao = db.skillDao();
            dao.delete(skills[0]);
            return null;
        }
    }
}
