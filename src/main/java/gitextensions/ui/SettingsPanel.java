package gitextensions.ui;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.util.ui.JBUI;
import gitextensions.GitExtensionsSettings;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel {

    private final int MARGIN = 2;

    private JPanel mainPanel;
    private TextFieldWithBrowseButton browseComponent;
    private JCheckBox displayBranchName;

    public SettingsPanel() {
        mainPanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        JPanel browsePanel = getBrowsePanel();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = JBUI.insets(MARGIN);
        panel.add(browsePanel, c);

        displayBranchName = new JCheckBox("Display current branch name on the Commit button");
        displayBranchName.setAlignmentY(Component.TOP_ALIGNMENT);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = JBUI.insets(MARGIN);
        panel.add(displayBranchName, c);

        mainPanel.add(panel, BorderLayout.NORTH);
    }

    @NotNull
    private JPanel getBrowsePanel() {
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
        c.insets = JBUI.insets(MARGIN);
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
        c.insets = JBUI.insets(MARGIN);
        panel.add(browseComponent, c);
        return panel;
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public void init(GitExtensionsSettings settings) {
        browseComponent.setText(settings.getExecutablePath());
        displayBranchName.setSelected(settings.isDisplayBranchName());
    }

    public String getExecutablePath() {
        return browseComponent.getText();
    }

    public boolean getDisplayBranchName() {
        return displayBranchName.isSelected();
    }

    public GitExtensionsSettings getSettings() {
        GitExtensionsSettings settings = new GitExtensionsSettings();
        settings.setExecutablePath(getExecutablePath());
        settings.setDisplayBranchName(getDisplayBranchName());
        return settings;
    }
}
