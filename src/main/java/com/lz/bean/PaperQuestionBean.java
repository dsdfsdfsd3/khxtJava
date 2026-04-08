package com.lz.bean;

import java.io.Serializable;
import lombok.*;

/**
 * @author  jtx
 * @date 2026-01-26 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PaperQuestionBean  implements Serializable {

	private static final long serialVersionUID =  27621674678996225L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 试卷ID
	 */
	private Long paperId;

	/**
	 * 题目ID
	 */
	private Long questionId;

	/**
	 * 该题分值
	 */
	private Long score;

	/**
	 * 题目顺序
	 */
	private Long sort;

}
