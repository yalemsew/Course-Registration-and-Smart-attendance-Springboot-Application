package edu.miu.courseregistrationcore.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Audit {

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private String createdBy;

    private String updatedBy;
}