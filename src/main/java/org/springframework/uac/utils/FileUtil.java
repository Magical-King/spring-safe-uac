package org.springframework.uac.utils;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.io.Resources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Sir.D
 */
public class FileUtil {

    public static File[] getFiles(String var0) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(var0);
        if (resources.length>0) {
            File[] files = new File[resources.length];
            for (int i = 0; i < resources.length; i++) {
                InputStream stream = resources[i].getInputStream();
                String path = System.getProperty("user.home") + File.separator + resources[i].getFilename();
                File file = new File(path);
                FileUtils.copyInputStreamToFile(stream, file);
                files[i] = file;
            }
            return files;
        }
        return null;
    }

}
