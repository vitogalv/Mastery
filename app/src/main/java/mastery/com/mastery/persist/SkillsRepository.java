package mastery.com.mastery.persist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class SkillsRepository {

    private static SkillsRepository instance;

    private SkillDAO skillDAO;
    private LiveData<List<Skill>> skills;

    private SkillsRepository(Application application){
        MasteryDB masteryDb = MasteryDB.getInstance(application);
        skillDAO = masteryDb.skillDao();
        skills = skillDAO.getAll();
    }

    public static SkillsRepository getInstance(Application application){
        if(instance != null){
            return instance;
        }
        instance = new SkillsRepository(application);
        return instance;
    }

    public LiveData<List<Skill>> getSkills() {
        return skills;
    }

    public LiveData<Skill> getSkill(String title){
        return skillDAO.getSkill(title);
    }

    public void addSkill(Skill skill){
        new AddSkillAsyncTask(skillDAO).execute(skill);
    }

    public void updateSkill(Skill skill){
        new UpdateSkillAsyncTask(skillDAO).execute(skill);
    }

    public void deleteSkill(String title){
        new DeleteSkillAsyncTask(skillDAO).execute(title);
    }

    public void deleteAll(){
        new DeleteAllAsyncTask(skillDAO).execute();
    }



    private static class AddSkillAsyncTask extends AsyncTask<Skill, Void, Void> {

        private SkillDAO dao;

        public AddSkillAsyncTask(SkillDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Skill... skills) {
            dao.insertSkill(skills[0]);
            return null;
        }
    }

    private static class UpdateSkillAsyncTask extends AsyncTask<Skill, Void, Void> {

        private SkillDAO dao;

        public UpdateSkillAsyncTask(SkillDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Skill... skills) {
            dao.updateSkill(skills[0]);
            return null;
        }
    }

    private static class DeleteSkillAsyncTask extends AsyncTask<String, Void, Void> {

        private SkillDAO dao;

        public DeleteSkillAsyncTask(SkillDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(String... titles) {
            dao.delete(titles[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private SkillDAO dao;

        public DeleteAllAsyncTask(SkillDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }
}
