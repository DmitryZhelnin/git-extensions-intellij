package gitextensions.commands;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Browse extends BaseAction {
    public Browse() {
        super(Commands.BROWSE);
    }

    @Nullable
    @Override
    protected String getFileNameFromEvent(@NotNull AnActionEvent e) {
        String result = super.getFileNameFromEvent(e);
        if (result == null) {
            Project project = e.getData(PlatformDataKeys.PROJECT);
            if (project != null) {
                result = project.getBasePath();
            }
        }
        return result;
    }
}
