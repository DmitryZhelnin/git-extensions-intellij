package gitextensions.ui;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.util.ui.JBUI;
import gitextensions.GitExtensionsSettings;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel {

    private final int MARGIN = 2;

    private JPanel mainPanel;
    private TextFieldWithBrowseButton browseComponent;
    private JCheckBox displayBranchName;
    private JSpinner maxLengthSpinner;

    public SettingsPanel() {
        mainPanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());

        JPanel browsePanel = getBrowsePanel();
        panel.add(browsePanel, getGridConstraints(0, 0, 1, 0));

        displayBranchName = new JCheckBox("Display current branch name on the Commit button");
        displayBranchName.setAlignmentY(Component.TOP_ALIGNMENT);
        displayBranchName.addActionListener(e -> {
            AbstractButton abstractButton = (AbstractButton) e.getSource();
            boolean selected = abstractButton.getModel().isSelected();
            maxLengthSpinner.setEnabled(selected);
        });
        panel.add(displayBranchName, getGridConstraints(0, 1, 1, 0));

        JPanel maxLengthPanel = getMaxBranchNameLengthPanel();
        panel.add(maxLengthPanel, getGridConstraints(0, 2, 1, 0));

        mainPanel.add(panel, BorderLayout.NORTH);
    }

    private JPanel getBrowsePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel label = new JLabel("GitExtensions executable path:", SwingConstants.LEFT);
        panel.add(label, getGridConstraints(0, 0, 0, 0));

        browseComponent = new TextFieldWithBrowseButton();
        FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor("exe");
        browseComponent.addBrowseFolderListener(null, null, null, descriptor);
        panel.add(browseComponent, getGridConstraints(1, 0, 1, 0));

        return panel;
    }

    private JPanel getMaxBranchNameLengthPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel label = new JLabel("Maximum branch name length, symbols:", SwingConstants.LEFT);
        panel.add(label, getGridConstraints(0, 0, 0, 0));

        SpinnerModel model = new SpinnerNumberModel(27, 5, 256, 1);
        maxLengthSpinner = new JSpinner(model);
        panel.add(maxLengthSpinner, getGridConstraints(1, 0, 1, 0));

        return panel;
    }

    private GridBagConstraints getGridConstraints(int x, int y, double weightX, double weightY) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = weightX;
        c.weighty = weightY;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = JBUI.insets(MARGIN);
        return c;
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public void init(GitExtensionsSettings settings) {
        browseComponent.setText(settings.getExecutablePath());
        displayBranchName.setSelected(settings.isDisplayBranchName());
        maxLengthSpinner.setValue(settings.getMaxBranchNameLength());
    }

    public String getExecutablePath() {
        return browseComponent.getText();
    }

    public boolean getDisplayBranchName() {
        return displayBranchName.isSelected();
    }

    public int getMaxBranchNameLength() {
        return (int) maxLengthSpinner.getValue();
    }

    public GitExtensionsSettings getSettings() {
        GitExtensionsSettings settings = new GitExtensionsSettings();
        settings.setExecutablePath(getExecutablePath());
        settings.setDisplayBranchName(getDisplayBranchName());
        settings.setMaxBranchNameLength(getMaxBranchNameLength());
        return settings;
    }
}
