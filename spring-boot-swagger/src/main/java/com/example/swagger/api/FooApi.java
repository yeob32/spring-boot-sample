package com.example.swagger.api;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "foo_v1")
public class FooApi {

    @ApiOperation(value = "findFoo", notes = "find Foo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Not Found Foo")
    })
    @GetMapping("/foo/{id}")
    public String foo(@ApiParam(value = "foo ID", required = true) @PathVariable Long id) {
        return "foo";
    }
}
