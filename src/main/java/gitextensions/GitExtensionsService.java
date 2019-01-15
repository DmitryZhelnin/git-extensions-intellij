package gitextensions;

import com.google.common.base.Strings;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.Nullable;
import utils.Registry;

public class GitExtensionsService {

    private static final String EXE_PATH = "GitExtensions.ExecutablePath";
    private static final String DISPLAY_BRANCH_NAME = "GitExtensions.DisplayBranchName";
    private static final String MAX_BRANCH_NAME_LENGTH = "GitExtensions.MaxBranchNameLength";

    private GitExtensionsSettings settings;

    private GitExtensionsService() {
        PropertiesComponent properties = PropertiesComponent.getInstance();

        String executablePath = properties.getValue(EXE_PATH);
        if (Strings.isNullOrEmpty(executablePath)) {
            executablePath = loadExecutablePathFromRegistry();
        }

        boolean displayBranchName = properties.getBoolean(DISPLAY_BRANCH_NAME, true);
        int maxBranchNameLength = properties.getInt(MAX_BRANCH_NAME_LENGTH, 27);

        settings = new GitExtensionsSettings();
        settings.setExecutablePath(executablePath);
        settings.setDisplayBranchName(displayBranchName);
        settings.setMaxBranchNameLength(maxBranchNameLength);
    }

    public static GitExtensionsService getInstance() {
        return ServiceManager.getService(GitExtensionsService.class);
    }

    public GitExtensionsSettings getSettings() {
        return settings;
    }

    public void saveSettings(GitExtensionsSettings newSettings) {
        PropertiesComponent properties = PropertiesComponent.getInstance();
        properties.setValue(EXE_PATH, newSettings.getExecutablePath());
        properties.setValue(DISPLAY_BRANCH_NAME, newSettings.isDisplayBranchName(), true);
        properties.setValue(MAX_BRANCH_NAME_LENGTH, newSettings.getMaxBranchNameLength(), 27);
        settings = newSettings;
    }

    @Nullable
    private String loadExecutablePathFromRegistry() {
        String executablePath;
        String installDir = Registry.read("HKEY_CURRENT_USER\\Software\\GitExtensions", "InstallDir");
        if (installDir == null) {
            installDir = Registry.read("HKEY_USERS\\Software\\GitExtensions", "InstallDir");
        }
        if (installDir == null) {
            executablePath = null;
        } else {
            executablePath = installDir + "\\GitExtensions.exe";
        }
        return executablePath;
    }

}

