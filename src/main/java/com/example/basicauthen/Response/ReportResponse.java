package com.example.basicauthen.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private String dateMessage;
    private int codigoMessage;
    private Long processid;
    private String codeRejection;
    public ReportResponse(String dateMessage, int codigoMessage, Long processid) {
        this.dateMessage = dateMessage;
        this.codigoMessage = codigoMessage;
        this.processid = processid;
    }
}
