package mastery.com.mastery.persist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface SkillDAO {

    @Insert
    void insertSkill(Skill skill);

    @Update
    void updateSkill(Skill skill);

    @Query("SELECT * FROM Skill WHERE title = :title")
    LiveData<Skill> getSkill(String title);

    @Query("SELECT * FROM Skill WHERE id = :id")
    LiveData<Skill> getSkill(long id);

    @Query("SELECT * FROM Skill ORDER BY last_commit")
    LiveData<List<Skill>> getAll();

    @Delete
    void delete(Skill... skills);

    @Query("DELETE FROM Skill WHERE title = :title")
    void delete(String title);

    @Query("DELETE FROM Skill")
    void deleteAll();

}
