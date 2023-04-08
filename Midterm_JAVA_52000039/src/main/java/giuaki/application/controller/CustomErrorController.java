package giuaki.application.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.aspectj.bridge.MessageUtil.error;

@Controller
public class CustomErrorController implements ErrorController {
    @GetMapping(value = "/error")
    public String ErrorManagement(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) == null ? 500 : request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE) == null ? "Internal Server Error" : request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        model.addAttribute("errorMsg",errorMessage);
        model.addAttribute("errorCode",status);
        return "error";
    }
}