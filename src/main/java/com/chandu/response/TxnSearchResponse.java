package com.chandu.response;

import com.chandu.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TxnSearchResponse {

    private User user;
    private Double expenditureAmount;
    private String expenseType;
    private String expenseDate;
}
