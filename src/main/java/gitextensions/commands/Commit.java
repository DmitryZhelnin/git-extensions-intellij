package gitextensions.commands;

import com.google.common.base.Strings;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.CustomComponentAction;
import com.intellij.openapi.actionSystem.impl.ActionButtonWithText;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import gitextensions.BranchNameService;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Objects;

public class Commit extends BaseAction implements CustomComponentAction {

    private static final int BRANCH_CHECK_TIMEOUT = 1000;
    private String lastBranchName;
    private long lastCheckMillis;

    public Commit() {
        super(Commands.COMMIT);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if (file == null || System.currentTimeMillis() - lastCheckMillis < BRANCH_CHECK_TIMEOUT) {
            return;
        }
        String branchName = BranchNameService.getInstance().getBranchName(file);
        if (!Objects.equals(lastBranchName, branchName)) {
            Presentation presentation = e.getPresentation();
            String text = Strings.isNullOrEmpty(branchName) ? "Commit" : String.format("Commit (%s)", branchName.replace("_", "__"));
            presentation.setText(text);
            lastBranchName = branchName;
        }
        lastCheckMillis = System.currentTimeMillis();
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
