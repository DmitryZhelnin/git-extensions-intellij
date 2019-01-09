import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseAction extends AnAction {

    private static final String ERROR_MESSAGE = "This plugin requires Git Extensions to be installed. This application can be downloaded from http://gitextensions.github.io/";

    private String command;

    BaseAction(@NotNull String command) {
        this.command = command;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
            String fileName = getFileName(file);

            if (fileName != null) {
                String path = GitExtensionsService.getInstance().getInstallDir();
                if (path == null) {
                    Messages.showMessageDialog(ERROR_MESSAGE, "Error", Messages.getErrorIcon());
                }
                List<String> args = new ArrayList<>(Arrays.asList(path, command, fileName));
                String additionalArgs = getAdditionalParameters(e);
                if (additionalArgs != null) {
                    args.add(additionalArgs);
                }

                new ProcessBuilder(args).start();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected String getFileName(@Nullable VirtualFile file) {
        return file != null ? file.getCanonicalPath() : null;
    }

    @Nullable
    protected String getAdditionalParameters(AnActionEvent e) {
        return null;
    }

}
