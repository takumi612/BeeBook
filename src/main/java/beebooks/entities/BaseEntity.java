package beebooks.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "status")
	private Boolean status = Boolean.TRUE;

//	@CreatedBy
	@Column(name = "created_by", updatable = false)
	private String createdBy;

//	@LastModifiedBy
	@Column(name = "updated_by")
	private String updatedBy;

//	@LastModifiedDate
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

//	@CreatedDate
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@PrePersist
	protected void onCreate() {
		this.createdDate = LocalDateTime.now();
		try {
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			if(name.equalsIgnoreCase("anonymousUser")) {
				this.createdBy = "Guest";
			}else{
				this.createdBy = name;
			}
		} catch (Exception e) {
			createdBy = "Guest";
		}
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedDate = LocalDateTime.now();
		try {
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			if(name.equalsIgnoreCase("anonymousUser")) {
				this.updatedBy = "Guest";
			}else{
				this.updatedBy = name;
			}
		} catch (Exception e) {
			updatedBy = "Guest";
		}
	}

}
