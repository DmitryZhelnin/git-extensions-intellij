package gitextensions.commands;

import com.google.common.base.Strings;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import gitextensions.GitExtensionsService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseAction extends AnAction {

    private static final String ERROR_MESSAGE = "Installed GitExtensions application is not found. You can set path to the executable file manually (File | Settings... | GitExtensions) or download and install GitExtensions from http://gitextensions.github.io/";

    private String command;

    public BaseAction(@NotNull String command) {
        this.command = command;
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT; //super.getActionUpdateThread();
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            String fileName = getFileNameFromEvent(e);

            if (fileName != null) {
                GitExtensionsService service = ApplicationManager.getApplication().getService(GitExtensionsService.class);
                String path = service.getSettings().getExecutablePath();
                if (Strings.isNullOrEmpty(path)) {
                    Messages.showMessageDialog(ERROR_MESSAGE, "Error", Messages.getErrorIcon());
                    return;
                }
                if (!new File(path).isFile()) {
                    Messages.showMessageDialog(String.format("File %s is not found", path), "Error", Messages.getErrorIcon());
                    return;
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

    @Nullable
    protected String getFileNameFromEvent(@NotNull AnActionEvent e) {
        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        return getFileName(file);
    }

    protected String getFileName(@Nullable VirtualFile file) {
        return file != null ? file.getCanonicalPath() : null;
    }

    @Nullable
    protected String getAdditionalParameters(AnActionEvent e) {
        return null;
    }

}
