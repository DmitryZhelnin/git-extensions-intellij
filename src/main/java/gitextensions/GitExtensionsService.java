package gitextensions;

import com.google.common.base.Strings;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.Nullable;
import utils.Registry;

import java.util.ArrayList;
import java.util.List;

public class GitExtensionsService {

    private static final String EXE_PATH = "GitExtensions.ExecutablePath";
    private static final String DISPLAY_BRANCH_NAME = "GitExtensions.DisplayBranchName";

    private List<SettingsListener> listeners = new ArrayList<SettingsListener>();
    private GitExtensionsSettings settings;

    private GitExtensionsService() {
        String executablePath = PropertiesComponent.getInstance().getValue(EXE_PATH);
        if (Strings.isNullOrEmpty(executablePath)) {
            executablePath = loadExecutablePathFromRegistry();
        }

        boolean displayBranchName = PropertiesComponent.getInstance().getBoolean(DISPLAY_BRANCH_NAME, true);

        settings = new GitExtensionsSettings();
        settings.setExecutablePath(executablePath);
        settings.setDisplayBranchName(displayBranchName);
    }

    public static GitExtensionsService getInstance() {
        return ServiceManager.getService(GitExtensionsService.class);
    }

    public GitExtensionsSettings getSettings() {
        return settings;
    }

    public void saveSettings(GitExtensionsSettings newSettings) {
        PropertiesComponent.getInstance().setValue(EXE_PATH, newSettings.getExecutablePath());
        PropertiesComponent.getInstance().setValue(DISPLAY_BRANCH_NAME, newSettings.isDisplayBranchName(), true);
        settings = newSettings;
        for (SettingsListener listener : listeners) {
            listener.settingsChanged(settings);
        }
    }

    public void addSettingsListener(SettingsListener listener) {
        listeners.add(listener);
    }

    public void removeSettingsListener(SettingsListener listener) {
        listeners.remove(listener);
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

