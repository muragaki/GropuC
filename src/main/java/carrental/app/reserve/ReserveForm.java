package carrental.app.reserve;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 予約内容のフォーム
 * @author 奥井
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveForm {
	@NotNull
	private LocalDate startdate;
	@NotNull
	private LocalDate enddate;
	@NotNull
	private Integer carid;
	@NotBlank
	private String fullnameid;
	@NotNull
	private LocalDateTime reservetime;
	@NotNull
	private Integer amount;
	
}
