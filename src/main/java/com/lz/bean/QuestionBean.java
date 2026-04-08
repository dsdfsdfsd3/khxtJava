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

public class QuestionBean  implements Serializable {

	private static final long serialVersionUID =  2797517155924898236L;

	/**
	 * 题目ID
	 */

	private Long id;

	/**
	 * 题型：single-单选 multiple-多选 short-简答
	 */
	private String type;

	/**
	 * 题目内容
	 */
	private String content;

	/**
	 * 选项（JSON格式，单选/多选使用）
	 */
	private String options;

	/**
	 * 正确答案（客观题使用）
	 */
	private String answer;

	/**
	 * 答案解析
	 */
	private String analysis;

	/**
	 * 默认分值
	 */
	private Long defaultScore;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
