package com.chandu.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticalResponse {

    private String userEmail;
    private Double oneDayAmount;
    private Double sevenDayAmount;
    private Double fifteenDayAmount;

}
