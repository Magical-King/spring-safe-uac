package org.springframework.uac.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author dsir
 */
@Data
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 2393269568666085258L;

	@Transient
	@TableField(exist = false)
	private Integer start = 1;

	@Transient
	@TableField(exist = false)
	private Integer limit = 10;

	@Transient
	@TableField(exist = false)
	private String property;

	@Transient
	@TableField(exist = false)
	private boolean asc;

	public static long getSerialVersionId() {
		return serialVersionUID;
	}

	public String camelToUnderline() {
		return StringUtils.camelToUnderline(property);
	}
}
