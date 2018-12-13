package mastery.com.mastery.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import mastery.com.mastery.R;
import mastery.com.mastery.persist.Skill;

public class SkillStatsView extends SkillView {

    TextView hours;

    public SkillStatsView(Context context, Skill skill){
        super(context);
        this.skill = skill;
        init();
    }

    public SkillStatsView(Context context) {
        super(context);
        init();
    }

    public SkillStatsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SkillStatsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_stats_skill, this);
        levelText = findViewById(R.id.level);
        hours = findViewById(R.id.hours);
        titleText = findViewById(R.id.rank);
        progressBar = findViewById(R.id.progressBar);
        update();
    }

    public void update(){
        if(skill == null){
            return;
        }
        titleText.setText(skill.getRank());
        levelText.setText(Integer.toString(skill.level));
        hours.setText(Integer.toString(skill.getHours()));
        progressBar.setProgress(skill.getProgress());
    }
}
