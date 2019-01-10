package gitextensions.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.Comparing;
import gitextensions.GitExtensionsService;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GitExtensionsSettingsConfigurable implements Configurable {

    private static final String NAME = "GitExtensions";

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
        settingsPanel.init(GitExtensionsService.getInstance().getSettings());
        return settingsPanel.getPanel();
    }

    @Override
    public boolean isModified() {
        return settingsPanel != null &&
            !Comparing.equal(GitExtensionsService.getInstance().getExecutablePath(), settingsPanel.getExecutablePath());
    }

    @Override
    public void apply() throws ConfigurationException {
        GitExtensionsService.getInstance().saveSettings(settingsPanel.getSettings());
    }

    @Override
    public void reset() {
        settingsPanel.init(GitExtensionsService.getInstance().getSettings());
    }
}

