package mastery.com.mastery.persist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;


public class SkillsViewModel extends AndroidViewModel {

    private SkillsRepository skillsRepo;

    public SkillsViewModel(Application application){
        super(application);
        skillsRepo = SkillsRepository.getInstance(application);
    }

    public LiveData<List<Skill>> getSkills(){
        return skillsRepo.getSkills();
    }

    public void addSkill(Skill skill){
        skillsRepo.addSkill(skill);
    }

    public void deleteSkill(String title){
        skillsRepo.deleteSkill(title);
    }

    public void deleteAll(){
        skillsRepo.deleteAll();
    }

}
