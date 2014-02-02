package com.tss;

import com.atlassian.bamboo.chains.StageExecution;
import com.atlassian.bamboo.chains.plugins.PreJobAction;
import com.atlassian.bamboo.commit.CommitContext;
import com.atlassian.bamboo.commit.CommitFile;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.v2.build.BuildChanges;
import com.atlassian.bamboo.v2.build.BuildContext;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Andrey Levchenko
 */
public class ChangedPathPreJobAction implements PreJobAction{
    private static final String CONFIG_ENTRY_NAME = "custom.changed.path.regexp";
    private static final String CHECKOUT_TASK_TYPE = "com.atlassian.bamboo.plugins.vcs:task.vcs.checkout";
    
    private static Logger log = Logger.getLogger(ChangedPathPreJobAction.class);
    

    @Override
    public void execute(StageExecution se, BuildContext bc) {
        String regexp = (String) bc.getBuildDefinition().getCustomConfiguration().get(CONFIG_ENTRY_NAME);
        
        if (StringUtils.isNotEmpty(regexp) && !isChangesMatched(bc.getBuildChanges(), regexp)){
            List<TaskDefinition> tasks = bc.getBuildDefinition().getTaskDefinitions();

            for (TaskDefinition task : tasks){
                if (!CHECKOUT_TASK_TYPE.equals(task.getPluginKey())){
                    task.setEnabled(false);
                }
            }
        }
        
    }
    private boolean isChangesMatched(BuildChanges changes, String regexp){
        log.debug("Filtering path by: " + regexp);
        for (CommitContext commit : changes.getChanges()){
            for (CommitFile file :commit.getFiles()){
                log.debug("checking path: " + file.getName());
                if (file.getName().matches(regexp)){
                    log.debug("found match: " + file.getName());
                    return true;
                }
            }
        }
        return false;
    }

}
