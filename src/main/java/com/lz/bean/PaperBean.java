

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

public class PaperBean  implements Serializable {

	private static final long serialVersionUID =  7387682376148264552L;

	/**
	 * 试卷ID
	 */

	private Long id;

	/**
	 * 试卷名称
	 */
	private String title;

	/**
	 * 试卷说明
	 */
	private String description;

	/**
	 * 试卷总分
	 */
	private Long totalScore;

	/**
	 * 考试时长（分钟）
	 */
	private Long duration;

	/**
	 * 创建人（管理员ID）
	 */
	private Long createBy;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
