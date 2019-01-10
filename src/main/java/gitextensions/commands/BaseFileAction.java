package gitextensions.commands;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public abstract class BaseFileAction extends BaseAction {

    public BaseFileAction(@NotNull String command) {
        super(command);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if (file != null) {
            Presentation presentation = e.getPresentation();
            presentation.setEnabled(!file.isDirectory());
        }
    }
}
