package mastery.com.mastery.components;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import mastery.com.mastery.R;
import mastery.com.mastery.SkillHub;
import mastery.com.mastery.persist.Skill;


/**
 * A SkillView is a compound view consisting of the title of the skill represented by {@link Skill},
 * along with the user's current rank on that Skill and a progress bar indicating his progress
 * towards getting his next level upgrate
 */
public class SkillView extends ConstraintLayout {

    Skill skill;

    protected TextView titleText;
    protected TextView levelText;
    protected ProgressBar progressBar;

    public SkillView(Context context, Skill skill){
        super(context);
        setSkill(skill);
        init();
    }
    public SkillView(Context context) {
        super(context);
        init();
    }

    public SkillView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SkillView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        inflate(getContext(), R.layout.view_skill, this);
        titleText = (TextView)findViewById(R.id.skill_title);
        levelText = (TextView)findViewById(R.id.level);
        progressBar = (ProgressBar)findViewById(R.id.level_progress);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SkillHub.class);
                intent.putExtra("skill_name", skill.id);
                getContext().startActivity(intent);
            }
        });
        update();
    }

    /**
     * @param skill the skill that this component is going to display information about
     */
    public void setSkill(Skill skill){
        this.skill = skill;
    }

    public Skill getSkill(){
        return skill;
    }

    public void update(){
        if(skill == null){
            return;
        }
        titleText.setText(skill.title);
        levelText.setText(Integer.toString(skill.level));
        progressBar.setProgress(skill.getProgress());
    }
}
