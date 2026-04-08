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
public class AnswerBean  implements Serializable {

	private static final long serialVersionUID =  3031505722419738246L;

	/**
	 * 答题ID
	 */

	private Long id;

	/**
	 * 用户试卷ID
	 */
	private Long userPaperId;

	/**
	 * 题目ID
	 */
	private Long questionId;

	/**
	 * 用户作答内容
	 */
	private String answerContent;

	/**
	 * 该题得分
	 */
	private Long score;

	/**
	 * 是否正确：1-正确 0-错误
	 */
	private Integer isCorrect;

	/**
	 * 答题时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date answerTime;

}
