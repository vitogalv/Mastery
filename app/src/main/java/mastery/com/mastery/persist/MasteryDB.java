package mastery.com.mastery.persist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Skill.class}, version = 1)
public abstract class MasteryDB extends RoomDatabase {

    private static MasteryDB instance;

    public abstract SkillDAO skillDao();

    public static MasteryDB getInstance(Context context){
        if(instance == null){
            RoomDatabase.Builder builder = Room.databaseBuilder(context,
                    MasteryDB.class, "master_db");
            instance = (MasteryDB)builder.build();
        }
        return instance;
    }
}
