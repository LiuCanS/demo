import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.net.URL;

public class Test {

    String getFilePath(){
        ApplicationHome h = new ApplicationHome(this.getClass());
        File jarF = h.getSource();
        return jarF.getParentFile().toString();
    }

    public void getInstance() {
        URL resource = getClass().getResource("");
        return ;
    }
}
