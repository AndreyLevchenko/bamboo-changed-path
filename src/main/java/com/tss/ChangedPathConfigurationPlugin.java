package com.tss;

import com.atlassian.bamboo.build.Job;
import com.atlassian.bamboo.plan.Plan;
import com.atlassian.bamboo.v2.build.BaseBuildConfigurationAwarePlugin;
import com.atlassian.bamboo.v2.build.configuration.MiscellaneousBuildConfigurationPlugin;

/**
 *
 * @author Andrey Levchenko
 */
public class ChangedPathConfigurationPlugin extends BaseBuildConfigurationAwarePlugin implements  MiscellaneousBuildConfigurationPlugin  {


    @Override
    public boolean isApplicableTo(Plan plan) {
        return plan instanceof Job;
    }


}
