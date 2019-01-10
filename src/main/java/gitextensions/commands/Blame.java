package gitextensions.commands;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.Nullable;

public class Blame extends BaseFileAction {
    public Blame() {
        super(Commands.BLAME);
    }

    @Nullable
    @Override
    protected String getAdditionalParameters(AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor != null && editor.getSelectionModel().hasSelection()) {
            int selectionStart = editor.getSelectionModel().getSelectionStart();
            int lineNumber = editor.getDocument().getLineNumber(selectionStart);
            return String.valueOf(lineNumber);
        }
        return null;
    }
}
