package com.inn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveRejectDTO {

    @NotEmpty(message = "Purchase Id cannot be null or empty.")
    @Size(min = 5, max = 10, message = "Purchase Id length must be between 5 and 10 characters.")
    @Schema(description = "Purchase Id", example = "PO-00000")
    private String purchaseId;

    @NotEmpty(message = "Username cannot be null or empty.")
    @Size(min = 5, max = 30, message = "Username length must be between 5 and 30 characters.")
    @Schema(description = "Username", example = "sarfarazalam")
    private String username;

    @Pattern(regexp = "^(Approved|Rejected)$", message = "Status must be either 'Approved' or 'Rejected'.")
    @Schema(description = "Status", example = "Approved")
    private String status;

    @Schema(description = "Reason", example = "All good")
    private String reason;
}
