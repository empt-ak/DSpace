package cz.muni.ics.dspace5.impl.io;

/**
 * @author Dominik Szalai - emptulik at gmail.com on 6/23/16.
 */
public class CLIOptionImpl implements CLIOption
{
    private String option;
    private String description;
    private boolean required;
    private boolean hasArgs;

    @Override
    public String getOption()
    {
        return option;
    }

    public void setOption(String option)
    {
        this.option = option;
    }

    @Override
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public boolean isRequired()
    {
        return required;
    }

    public void setRequired(boolean required)
    {
        this.required = required;
    }

    @Override
    public boolean hasArgs()
    {
        return hasArgs;
    }

    public void setHasArgs(boolean hasArgs)
    {
        this.hasArgs = hasArgs;
    }

    @Override
    public String toString()
    {
        return "CLIOptionImpl{" +
                "option='" + option + '\'' +
                ", description='" + description + '\'' +
                ", required=" + required +
                ", hasArgs=" + hasArgs +
                '}';
    }
}
