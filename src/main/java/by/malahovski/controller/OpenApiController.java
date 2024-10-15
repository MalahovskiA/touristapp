package by.malahovski.controller;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenApiController {

    private final OpenAPI openAPI;

    @Autowired
    public OpenApiController(OpenAPI openAPI) {
        this.openAPI = openAPI;
    }

    @GetMapping("/v3/api-docs")
    public String getOpenApiJson() {
        return Json.pretty(openAPI);
    }
}