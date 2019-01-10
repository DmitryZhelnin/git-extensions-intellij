package gitextensions;

import com.google.common.base.Strings;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.Nullable;
import utils.Registry;

public class GitExtensionsService {

    private static final String EXE_PATH = "GitExtensions.ExecutablePath";

    private GitExtensionsSettings settings;

    private GitExtensionsService() {
        String executablePath = PropertiesComponent.getInstance().getValue(EXE_PATH);
        if (Strings.isNullOrEmpty(executablePath)) {
            String installDir = Registry.read("HKEY_CURRENT_USER\\Software\\GitExtensions", "InstallDir");
            if (installDir == null) {
                installDir = Registry.read("HKEY_USERS\\Software\\GitExtensions", "InstallDir");
            }
            if (installDir == null) {
                executablePath = null;
            } else {
                executablePath = installDir + "\\GitExtensions.exe";
            }
        }
        settings = new GitExtensionsSettings();
        settings.setExecutablePath(executablePath);
    }

    public static GitExtensionsService getInstance() {
        return ServiceManager.getService(GitExtensionsService.class);
    }

    @Nullable
    public String getExecutablePath() {
        return settings.getExecutablePath();
    }

    public GitExtensionsSettings getSettings() {
        return settings;
    }

    public void saveSettings(GitExtensionsSettings newSettings) {
        PropertiesComponent.getInstance().setValue(EXE_PATH, newSettings.getExecutablePath());
        settings = newSettings;
    }

}

