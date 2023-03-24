package com.example.basicauthen.controller;

import com.example.basicauthen.Constant.EnableTokenPath;
import com.example.basicauthen.Response.ReportResponse;
import com.example.basicauthen.Response.TokenResponse;
import com.example.basicauthen.model.User;
import com.example.basicauthen.service.JWT.JwtService;
import com.example.basicauthen.service.Report.ReportService;
import com.example.basicauthen.service.UserDetailServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.basicauthen.model.Report;

@RestController
@RequestMapping
@Log4j2
public class TestController {
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    ReportService reportService;

    @PostMapping(EnableTokenPath.createToken)
    public TokenResponse login(@RequestBody User user) {
        //Authentication
        UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
        User userDB = userDetailService.findByIDSecret(user.getClient_id(), user.getClient_secret());
        if (userDetails != null && userDB != null){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //Create Token
            String token = jwtService.generateToken(userDetails);

            return new TokenResponse(token);
        }
        return null;
    }

    @PostMapping(EnableTokenPath.reportUser)
    public ReportResponse reportUser(@RequestBody Report report) {
        return reportService.handleReportUser(report);
    }

    @PostMapping(EnableTokenPath.report)
    public ReportResponse report(@RequestBody Report report) {
        return reportService.handleReport(report);
    }

    @GetMapping("/demo")
    public String demo(){
        return "Success";
    }

//    @GetMapping("/excelReader/{fileName}")
//    public List<List<String>> excelReader(@PathVariable String fileName) throws IOException {
//        // Extract the encoded username and password from the header
//        return ReadExcelFileService.readExcelFile(fileName);
//    }
}
