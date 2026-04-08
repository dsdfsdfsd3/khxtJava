package com.lz.bean;

import java.io.Serializable;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * @author  jtx
 * @date 2026-01-26 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserPaperBean  implements Serializable {

	private static final long serialVersionUID =  6453979969843859554L;

	/**
	 * 用户考卷ID
	 */

	private Long id;

	/**
	 * 试卷ID
	 */
	private Long paperId;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 考试开始时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	/**
	 * 考试结束时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	/**
	 * 状态：not_start/doing/finished
	 */
	private String status;

	/**
	 * 最终得分
	 */
	private Long totalScore;

	/**
	 * 生成时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
