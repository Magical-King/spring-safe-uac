package org.springframework.uac.core.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.common.properties.GzrobotProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.uac.model.domain.GzVersion;
import org.springframework.uac.service.GzSecurityConfigService;
import org.springframework.uac.service.GzVersionService;
import org.springframework.uac.utils.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author Sir.D
 */
@Slf4j
@Configuration
public class InitDataBase {

    @Value( value = "${spring.datasource.url}" ) private  String url;
    @Value( value = "${spring.datasource.username}" ) private String username;
    @Value( value = "${spring.datasource.password}" ) private String password;
    @Value( value = "${spring.datasource.driver-class-name}" ) private String driver;

    @Autowired
    private GzVersionService versionService;

    @Autowired
    private GzSecurityConfigService configService;

    @Autowired
    private GzrobotProperties properties;

    public void init() {
        Integer db = null;
        Integer modify = null;
        Integer trigger = null;

        if (!this.configService.exists(properties.getDb().getTableSchema())) {
            db = file("sql/db/*.sql", 0, ";");
            modify = file("sql/modify/*.sql", 0, ";");
            trigger = file("sql/trigger/*.sql", 0, ";;");
        }

        GzVersion version;
        if (null == db) {
            version = this.versionService.getById(1);
            db = file("sql/db/*.sql", version.getDb(), ";");
            modify = file("sql/modify/*.sql", version.getModify(), ";");
            trigger = file("sql/trigger/*.sql", 0, ";;");
        } else {
            version = new GzVersion();
        }

        version.setId(1);
        version.setDb(db);
        version.setModify(modify);
        version.setVersion(db + "." + trigger + modify);
        this.versionService.saveOrUpdate(version);

    }


    private Integer file(String path, Integer v1, String delimiter) {
        if (null != v1) {
            try {
                File[] files = FileUtil.getFiles(path);
                if (null == files) {
                    return v1;
                }

                File[] var4 = files;
                int var5 = files.length;
                Integer var7 = null;

                for(int var6 = 0; var6 < var5; ++var6) {
                    File file = var4[var6];
                    String v2 = file.getName().replace(".sql", "");
                    var7 = Integer.valueOf(v2);
                    if (var7.compareTo(v1) > 0) {
                        this.execute(file, delimiter);
                    }

                    file.delete();
                }

                return var7;
            } catch (IOException var9) {
                var9.printStackTrace();
            }
        }
        return v1;
    }

    /**
     * 执行脚本
     * @param delimiter
     */
    private void execute(File file, String delimiter ){
        try {
            SQLExec exec = new SQLExec();
            exec.setPrint( false );
            exec.setEncoding( "UTF-8" );
            exec.setDelimiter( delimiter );
            exec.setProject( new Project() );
            exec.setUrl( url );
            exec.setUserid( username );
            exec.setPassword( password );
            exec.setDriver( driver );
            exec.setSrc( file );
            exec.execute();
        } catch ( Exception e ) {
            log.error( "==> Cannot execute script [ {} ], Cause : {}", file.getName(), e.getMessage() );
        }
    }

}
