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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TokenResponse> login(@RequestBody User user) {
        //Authentication
        UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
        User userDB = userDetailService.findByIDSecret(user.getClient_id(), user.getClient_secret());
        if (userDetails != null && userDB != null){

            //Create Token
            String token = jwtService.generateToken(userDetails);


            return new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK);
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

    @PostMapping(EnableTokenPath.demo)
    public ResponseEntity<String> demo(){
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping(EnableTokenPath.test)
    public ResponseEntity<String> decodeToken(@RequestBody String bearerToken){
        System.out.println(bearerToken);
        String value = jwtService.extractUserName(bearerToken) + " : " + jwtService.extractPassword(bearerToken);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

//    @GetMapping("/excelReader/{fileName}")
//    public List<List<String>> excelReader(@PathVariable String fileName) throws IOException {
//        // Extract the encoded username and password from the header
//        return ReadExcelFileService.readExcelFile(fileName);
//    }
}
