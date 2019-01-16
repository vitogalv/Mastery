package mastery.com.mastery.persist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

public class SkillViewModel extends AndroidViewModel {

    private SkillsRepository skillsRepo;
    private LiveData<Skill> skill;

    public SkillViewModel(@NonNull Application application) {
        super(application);
        skillsRepo = SkillsRepository.getInstance(application);
    }

    public void setSkill(String title){
        skill = skillsRepo.getSkill(title);
    }

    public LiveData<Skill> getSkill(){
        return skill;
    }

    public void updateSkill(Skill skill){
        skillsRepo.updateSkill(skill);
    }

    public void deleteSkill(String title){
        skillsRepo.deleteSkill(title);
    }
}
