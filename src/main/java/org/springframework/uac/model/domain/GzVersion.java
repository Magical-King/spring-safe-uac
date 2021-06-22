package org.springframework.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Sir.D
 * @since 2020-08-06
 */
@TableName("gz_version")
public class GzVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("version")
    private String version;

    @TableField("db")
    private Integer db;

    @TableField("modify")
    private Integer modify;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public Integer getDb() {
        return db;
    }

    public void setDb(Integer db) {
        this.db = db;
    }
    public Integer getModify() {
        return modify;
    }

    public void setModify(Integer modify) {
        this.modify = modify;
    }

    @Override
    public String toString() {
        return "GzVersion{" +
            "id=" + id +
            ", version=" + version +
            ", db=" + db +
            ", modify=" + modify +
        "}";
    }
}
