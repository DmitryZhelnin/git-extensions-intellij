package gitextensions.ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import gitextensions.GitExtensionsService;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GitExtensionsSettingsConfigurable implements Configurable {

    private static final String NAME = "GitExtensions";
    private final GitExtensionsService GitExtensions = ApplicationManager.getApplication().getService(GitExtensionsService.class);

    private SettingsPanel settingsPanel;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return NAME;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (settingsPanel == null) {
            settingsPanel = new SettingsPanel();
        }
        settingsPanel.init(GitExtensions.getSettings());
        return settingsPanel.getPanel();
    }

    @Override
    public boolean isModified() {
        return settingsPanel != null &&
            !GitExtensions.getSettings().equals(settingsPanel.getSettings());
    }

    @Override
    public void apply() throws ConfigurationException {
        GitExtensions.saveSettings(settingsPanel.getSettings());
    }

    @Override
    public void reset() {
        settingsPanel.init(GitExtensions.getSettings());
    }
}

