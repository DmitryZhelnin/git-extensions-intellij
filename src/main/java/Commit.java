import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.annotations.NotNull;

public class Commit extends BaseAction {
    public Commit() {
        super(Commands.COMMIT);
    }

    @Override
    public void beforeActionPerformedUpdate(@NotNull AnActionEvent e) {
        FileDocumentManager.getInstance().saveAllDocuments();
        super.beforeActionPerformedUpdate(e);
    }
}
