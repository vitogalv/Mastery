package mastery.com.mastery.persist;

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
    Skill getSkill(String title);

    @Query("SELECT * FROM Skill WHERE id = :id")
    Skill getSkill(long id);

    @Query("SELECT * FROM Skill ORDER BY last_commit")
    List<Skill> getAll();

    @Delete
    int delete(Skill... skills);
}
