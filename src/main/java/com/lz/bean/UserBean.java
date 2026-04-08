package com.lz.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author  jtx
 * @date 2026-01-26 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserBean  implements Serializable {

	private static final long serialVersionUID =  8146499290522312730L;

	/**
	 * 用户ID
	 */

	private Long id;

	/**
	 * 登录账号
	 */
	private String username;

	/**
	 * 密码（加密存储）
	 */
	private String password;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 角色ID：1-管理员 2-普通用户
	 */
	private Long roleId;

	/**
	 * 状态：1-正常 0-禁用
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
