package by.malahovski.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * HomeController is responsible for handling requests related to the home page of the application.
 * It serves the main landing page where users can access different features of the Tourist App.
 */
@Controller
@RequestMapping("/")
@Tag(name = "Home", description = "Operations related to the home page")
public class HomeController {

    /**
     * Handles GET requests to the root URL ("/") and returns the name of the view
     * to be rendered for the home page.
     *
     * @return the name of the home view
     */
    @GetMapping("/")
    @Operation(summary = "Get Home Page", description = "Returns the home view")
    public String home() {
        return "home";
    }
}