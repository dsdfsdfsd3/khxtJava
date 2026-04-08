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

public class MarkRecordBean  implements Serializable {

	private static final long serialVersionUID =  8793759127735399049L;

	/**
	 * 阅卷ID
	 */

	private Long id;

	/**
	 * 用户试卷ID
	 */
	private Long userPaperId;

	/**
	 * 阅卷人ID
	 */
	private Long markerId;

	/**
	 * 评语
	 */
	private String comment;

	/**
	 * 阅卷时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date markTime;

}
