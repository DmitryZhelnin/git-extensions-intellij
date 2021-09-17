package gitextensions;

import com.google.common.base.Strings;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.*;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public final class BranchNameService {

    private static final String GIT_DIR_PREFIX = "gitdir:";
    private static final String REF_PREFIX = "ref:";
    private static final String BRANCH_NAME_PREFIX = "refs/heads/";

    private final Map<String, String> fileName2BranchName;
    private final Set<String> gitHeadFileWatchSet;
    private final Project myProject;
    private String lastReturnedBranchName;

    private BranchNameService(Project project) {
        myProject = project;
        fileName2BranchName = new ConcurrentHashMap<>(512);
        gitHeadFileWatchSet = ConcurrentHashMap.newKeySet();
    }

    @Nullable
    public String getBranchName(VirtualFile file) {
        String fileName = file.getCanonicalPath();
        if (fileName2BranchName.containsKey(fileName)) {
            String name = fileName2BranchName.get(fileName);
            lastReturnedBranchName = name;
            return name;
        }
        new Thread(() -> determineBranchName(file)).start();
        return lastReturnedBranchName;
    }

    private void determineBranchName(VirtualFile file) {
        String fileName = file.getCanonicalPath();
        VirtualFile gitDir = findGitWorkingDir(file);
        if (gitDir == null) {
            fileName2BranchName.put(fileName, "");
            return;
        }
        String branchName = null;
        VirtualFile gitFile = gitDir.findChild(".git");
        if (gitFile != null) {
            VirtualFile headFile = processGitFile(gitFile);
            if (headFile != null) {
                 branchName = readHeadFile(headFile);
            }
        }
        fileName2BranchName.put(fileName, Strings.isNullOrEmpty(branchName) ?  "" : branchName);
    }

    @Nullable
    private VirtualFile findGitWorkingDir(VirtualFile file)
    {
        if (Strings.isNullOrEmpty(file.getCanonicalPath())) {
            return null;
        }

        VirtualFile dir = file.getParent();
        while (dir != null) {
            if (isValidWorkingDir(dir)) {
                return dir;
            }
            dir = dir.getParent();
        }

        return null;
    }

    private boolean isValidWorkingDir(VirtualFile file){
        String path = file.getCanonicalPath();
        if (Strings.isNullOrEmpty(path)) {
            return false;
        }

        return file.findChild(".git") != null;
    }

    private VirtualFile processGitFile(VirtualFile gitFile) {
        VirtualFile gitHeadFile = gitFile.findChild("HEAD");
        if (gitHeadFile != null) {
            registerFileChangedListener(gitHeadFile.getCanonicalPath());
            return gitHeadFile;
        } else {
            VirtualFile dirDir = checkGitWorkTree(gitFile);
            if (dirDir != null) {
                return processGitFile(dirDir);
            }
        }
        return null;
    }

    private VirtualFile checkGitWorkTree(VirtualFile gitFile) {
        try {
            String content = new String(gitFile.contentsToByteArray(), StandardCharsets.UTF_8);
            if (!content.contains(GIT_DIR_PREFIX)) {
                return null;
            }

            String gitDir = content.replace(GIT_DIR_PREFIX, "").trim();
            VirtualFile file = LocalFileSystem.getInstance().findFileByPath(gitDir);
            if (file == null) {
                String combinedPath = new File(gitFile.getParent().getCanonicalPath(), gitDir).getCanonicalPath();
                file = LocalFileSystem.getInstance().findFileByPath(combinedPath);
            }
            return file;

        } catch (Exception e) {
            // ignore
        }

        return null;
    }

    private String readHeadFile(VirtualFile headFile) {
        if (headFile != null) {
            try {
                String content = new String(headFile.contentsToByteArray(), StandardCharsets.UTF_8);
                if (!content.contains(REF_PREFIX)) {
                    return null;
                }

                return content.replace(REF_PREFIX, "").trim().replace(BRANCH_NAME_PREFIX, "").trim();

            } catch (Exception e) {
                // ignore
            }
        }

        return null;
    }

    private void registerFileChangedListener(final String gitHeadFilePath) {
        if (!gitHeadFileWatchSet.contains(gitHeadFilePath)) {
            gitHeadFileWatchSet.add(gitHeadFilePath);
            myProject.getMessageBus().connect().subscribe(VirtualFileManager.VFS_CHANGES, new BulkFileListener() {
                @Override
                public void after(@NotNull List<? extends VFileEvent> events) {
                    for (VFileEvent event : events) {
                        VirtualFile file = event.getFile();
                        if (file != null && gitHeadFilePath.equals(file.getCanonicalPath())) {
                            invalidateFilesCache();
                        }
                    }
                }
            });
        }
    }

    private void invalidateFilesCache() {
        fileName2BranchName.clear();
    }
}
