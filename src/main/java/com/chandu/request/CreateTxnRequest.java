package com.chandu.request;


import com.chandu.model.TxnDetails;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTxnRequest {

    @NotBlank(message = "user email can't be blank")
    private String userEmail;

    private String expenseType;
    private Double expenditureCost;
    private LocalDate expenseDate;

    private String expenseNote;


    public TxnDetails toTxnDetails(CreateTxnRequest createTxnRequest) {

        return TxnDetails.builder().
                expenditureAmount(this.expenditureCost).
                expenseDate(expenseDate).
                expenseNote(this.expenseNote).build();

    }

}
