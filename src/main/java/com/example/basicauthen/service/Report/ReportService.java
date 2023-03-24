package com.example.basicauthen.service.Report;

import com.example.basicauthen.Response.ReportResponse;
import com.example.basicauthen.model.Report;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    public ReportResponse handleReportUser(Report report) {
        boolean success = true;
        return success ?
                new ReportResponse(report.getDateMessage(), report.getCodigoMessage(), report.getProcessid())
                :
                new ReportResponse(report.getDateMessage(), report.getCodigoMessage(), report.getProcessid(), "RENRC000006");
    }
    public ReportResponse handleReport(Report report) {
        boolean success = true;
        return success ?
                new ReportResponse(report.getDateMessage(), report.getCodigoMessage(), report.getProcessid())
                :
                new ReportResponse(report.getDateMessage(), report.getCodigoMessage(), report.getProcessid(), "RENRC000006");
    }

}
