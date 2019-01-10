package gitextensions.ui;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.util.ui.JBUI;
import gitextensions.GitExtensionsSettings;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel {

    private JPanel mainPanel;
    private final TextFieldWithBrowseButton browseComponent;

    public SettingsPanel() {
        int margin = 2;

        mainPanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setAlignmentY(Component.TOP_ALIGNMENT);

        GridBagConstraints c = new GridBagConstraints();

        JLabel label = new JLabel("GitExtensions executable path:", SwingConstants.LEFT);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 0;
        c.weighty = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = JBUI.insets(margin);
        panel.add(label, c);

        browseComponent = new TextFieldWithBrowseButton();
        FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor("exe");
        browseComponent.addBrowseFolderListener(null, null, null, descriptor);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = JBUI.insets(margin);
        panel.add(browseComponent, c);

        mainPanel.add(panel, BorderLayout.NORTH);
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public void init(GitExtensionsSettings settings) {
        browseComponent.setText(settings.getExecutablePath());
    }

    public String getExecutablePath() {
        return browseComponent.getText();
    }

    public GitExtensionsSettings getSettings() {
        GitExtensionsSettings settings = new GitExtensionsSettings();
        settings.setExecutablePath(getExecutablePath());
        return settings;
    }
}
