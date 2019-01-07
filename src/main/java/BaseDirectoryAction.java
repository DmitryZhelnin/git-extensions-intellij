import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseDirectoryAction extends BaseAction {

    BaseDirectoryAction(@NotNull String command) {
        super(command);
    }

    @Override
    protected String getFileName(@Nullable VirtualFile file) {
        if (file == null) {
            return null;
        }
        if (file.isDirectory()) {
            return file.getCanonicalPath();
        }
        return file.getParent().getCanonicalPath();
    }
}
