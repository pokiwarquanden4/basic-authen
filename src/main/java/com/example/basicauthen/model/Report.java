package com.example.basicauthen.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private String dateMessage;
    private int codigoMessage;
    private Long processid;
    private Long imei;
    private Character action;
    private String actionService;

    public Report(String dateMessage, int codigoMessage, Long processid, Long imei, Character action) {
        this.dateMessage = dateMessage;
        this.codigoMessage = codigoMessage;
        this.processid = processid;
        this.imei = imei;
        this.action = action;
    }
}
