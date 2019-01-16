package mastery.com.mastery.persist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Skill implements Comparable<Skill> {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    public int level;

    public double minutes;

    @ColumnInfo(name="last_commit")
    public long lastCommit;

    public int getProgress(){
        return (int)(minutes / 1200) * 100;
    }

    public void incrementMinutes(double val){
        minutes += val;
        if(minutes >= 1200){
            minutes = minutes % 1200;
            level++;
        }
    }

    public String getRank(){
        if(level <= 10){
            return "Newbie I";
        }else if(level <= 20){
            return "Newbie II";
        }else if(level <= 60){
            return "Intermediate I";
        }else if(level < 100){
            return "Intermediate II";
        }else if(level < 200){
            return "Pro I";
        }else if(level < 300){
            return "Pro II";
        }else if(level < 400){
            return "Ninja I";
        }else if(level < 500){
            return "Ninja II";
        }
        return "Master";
    }

    public int getHours(){
        int inProgressHours = (int)(minutes / 60);
        if(level > 1){
            return  level * 20 + inProgressHours;
        }else{
            return inProgressHours;
        }
    }

    @Override
    public int compareTo(@NonNull Skill o) {
        if(lastCommit > o.lastCommit){
            return -1;
        }else if(lastCommit < o.lastCommit){
            return 1;
        }
        return 0;
    }
}
