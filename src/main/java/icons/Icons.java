package icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public interface Icons {
        Icon DIFF = IconLoader.getIcon("/icons/Diff.png", Icons.class);
        Icon FILE_HISTORY = IconLoader.getIcon("/icons/FileHistory.png", Icons.class);
        Icon RESET = IconLoader.getIcon("/icons/ResetWorkingDirChanges.png", Icons.class);
        Icon BROWSE = IconLoader.getIcon("/icons/BrowseFileExplorer.png", Icons.class);
        Icon CLONE = IconLoader.getIcon("/icons/CloneRepoGit.png", Icons.class);
        Icon INIT = IconLoader.getIcon("/icons/star.png", Icons.class);
        Icon COMMIT = IconLoader.getIcon("/icons/RepoStateClean.png", Icons.class);
        Icon PULL = IconLoader.getIcon("/icons/Pull.png", Icons.class);
        Icon PUSH = IconLoader.getIcon("/icons/Push.png", Icons.class);
        Icon STASH = IconLoader.getIcon("/icons/stash.png", Icons.class);
        Icon MANAGE_REMOTES = IconLoader.getIcon("/icons/Remotes.png", Icons.class);
        Icon EDIT_GITIGNORE = IconLoader.getIcon("/icons/EditGitIgnore.png", Icons.class);
        Icon APPLY_PATCH = IconLoader.getIcon("/icons/PatchApply.png", Icons.class);
        Icon FORMAT_PATCH = IconLoader.getIcon("/icons/PatchFormat.png", Icons.class);
        Icon BLAME = IconLoader.getIcon("/icons/Blame.png", Icons.class);
        Icon CHECKOUT = IconLoader.getIcon("/icons/checkout.png", Icons.class);
        Icon CREATE_BRANCH = IconLoader.getIcon("/icons/BranchCreate.png", Icons.class);
        Icon MERGE = IconLoader.getIcon("/icons/Merge.png", Icons.class);
        Icon REBASE = IconLoader.getIcon("/icons/Rebase.png", Icons.class);
        Icon CONFLICT = IconLoader.getIcon("/icons/Conflict.png", Icons.class);
        Icon CHERRY = IconLoader.getIcon("/icons/CherryPick.png", Icons.class);
        Icon GITBASH = IconLoader.getIcon("/icons/GitForWindows.png", Icons.class);
        Icon SETTINGS = IconLoader.getIcon("/icons/Settings.png", Icons.class);
        Icon INFO = IconLoader.getIcon("/icons/information.png", Icons.class);
}
