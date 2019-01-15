package gitextensions;

import java.util.Objects;

public class GitExtensionsSettings {

    private String executablePath;
    private boolean displayBranchName;
    private int maxBranchNameLength;

    public String getExecutablePath() {
        return executablePath;
    }

    public void setExecutablePath(String executablePath) {
        this.executablePath = executablePath;
    }

    public boolean isDisplayBranchName() {
        return displayBranchName;
    }

    public void setDisplayBranchName(boolean displayBranchName) {
        this.displayBranchName = displayBranchName;
    }

    public int getMaxBranchNameLength() {
        return maxBranchNameLength;
    }

    public void setMaxBranchNameLength(int maxBranchNameLength) {
        this.maxBranchNameLength = maxBranchNameLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GitExtensionsSettings that = (GitExtensionsSettings) o;
        return displayBranchName == that.displayBranchName &&
            maxBranchNameLength == that.maxBranchNameLength &&
            Objects.equals(executablePath, that.executablePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(executablePath, displayBranchName, maxBranchNameLength);
    }
}

