package org.springframework.uac.model.dto.review;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Sir.D
 */
@Data
public class ReviewDto {

    @NotNull(message = "review.reviewId.notnull")
    private Integer reviewId;

}
