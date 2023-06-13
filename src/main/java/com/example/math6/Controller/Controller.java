package com.example.math6.Controller;

import com.example.math6.DTO.FunctionInfo;
import com.example.math6.util.Analysis;
import com.example.math6.util.ChartGenerator;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.Session;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
    @GetMapping()
    public String getHome(){
        return "input";
    }

    @PostMapping("calcWithStep/{id}")
    public String calcWithStep(@PathVariable("id") int id, @RequestBody FunctionInfo functionInfo, HttpSession session){
        //System.out.println("I was here");
        System.out.println(id);
        Analysis analysis = new Analysis(functionInfo);
        var answer = analysis.analyzeStep(id);
        session.setAttribute("answers", answer);
        session.setAttribute("analysis", analysis);
        session.setAttribute("option", id);
        return "result";
    }

    @GetMapping(value = "/Chart1.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> newChart1(HttpSession session) throws IOException {
        Analysis analysis = (Analysis) session.getAttribute("analysis");
        Integer option = (Integer) session.getAttribute("option");
        var i = ChartGenerator.generateChart(analysis.generateCoordsForBase(option), analysis.generateCoordsForApproxEuler(option), "Euler Method");
        return ResponseEntity.ok().body(i);
    }

    @GetMapping(value = "/Chart2.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> newChart2(HttpSession session) throws IOException {
        Analysis analysis = (Analysis) session.getAttribute("analysis");
        Integer option = (Integer) session.getAttribute("option");
        var i = ChartGenerator.generateChart(analysis.generateCoordsForBase(option), analysis.generateCoordsForApproxRunge(option), "Runge Method");
        return ResponseEntity.ok().body(i);
    }

    @GetMapping(value = "/Chart3.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> newChart3(HttpSession session) throws IOException {
        Analysis analysis = (Analysis) session.getAttribute("analysis");
        Integer option = (Integer) session.getAttribute("option");
        var i = ChartGenerator.generateChart(analysis.generateCoordsForBase(option), analysis.generateCoordsForApproxMiln(option), "Miln Method");
        return ResponseEntity.ok().body(i);
    }

    /*
    @PostMapping("calcWithAccuracy/{id}")
    public String calcWithAccuracy(@PathVariable("id") int id, @RequestBody FunctionInfo functionInfo){
        System.out.println(id + " " + functionInfo);
        return "result";
    }

     */

    @GetMapping("result")
    public String getResult(HttpSession session, Model model){
        model.addAttribute("answers",session.getAttribute("answers"));
        return "result";
    }
}
