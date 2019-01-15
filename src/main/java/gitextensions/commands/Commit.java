package gitextensions.commands;

import com.google.common.base.Strings;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.CustomComponentAction;
import com.intellij.openapi.actionSystem.impl.ActionButtonWithText;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import gitextensions.BranchNameService;
import gitextensions.GitExtensionsService;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class Commit extends BaseAction implements CustomComponentAction {

    private GitExtensionsService GitExtensions = GitExtensionsService.getInstance();

    public Commit() {
        super(Commands.COMMIT);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        if (!GitExtensions.getSettings().isDisplayBranchName()) {
            if (e.isFromActionToolbar()) {
                e.getPresentation().setText("", false);
            } else if (e.isFromContextMenu()){
                e.getPresentation().setText("Commit", false);
            }
            return;
        }

        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (project == null || file == null) {
            return;
        }

        String branchName = BranchNameService.getInstance(project).getBranchName(file);
        Presentation presentation = e.getPresentation();
        String text;
        if (Strings.isNullOrEmpty(branchName)) {
            text = "Commit";
        } else {
            branchName = StringUtils.abbreviate(branchName, GitExtensions.getSettings().getMaxBranchNameLength());
            text = String.format("Commit (%s)", branchName);
        }
        presentation.setText(text, false);
    }

    @Override
    public void beforeActionPerformedUpdate(@NotNull AnActionEvent e) {
        FileDocumentManager.getInstance().saveAllDocuments();
        super.beforeActionPerformedUpdate(e);
    }

    @NotNull
    @Override
    public JComponent createCustomComponent(@NotNull Presentation presentation) {
        return new ActionButtonWithText(this, presentation, ActionPlaces.UNKNOWN, ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE);
    }

}
