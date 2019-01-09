import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.Nullable;
import utils.Registry;

public class GitExtensionsService {

    private String installDir;

    private GitExtensionsService() {
        installDir = Registry.read("HKEY_CURRENT_USER\\Software\\GitExtensions", "InstallDir");
        if (installDir == null) {
            installDir = Registry.read("HKEY_USERS\\Software\\GitExtensions", "InstallDir");
        }
        if (installDir == null) {
            return;
        }
        installDir += "\\GitExtensions.exe";
    }

    public static GitExtensionsService getInstance() {
        return ServiceManager.getService(GitExtensionsService.class);
    }

    @Nullable
    public String getInstallDir() {
        return installDir;
    }
}
