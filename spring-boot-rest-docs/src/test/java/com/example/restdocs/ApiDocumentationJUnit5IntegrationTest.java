package com.example.restdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
//@AutoConfigureRestDocs()
public class ApiDocumentationJUnit5IntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider)
                        .uris()
                        .withScheme("https")
                        .withHost("yeob32.com")
                        .withPort(443))
                // .alwaysDo(document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    public void indexExample() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andDo(document("index",
                        links(linkWithRel("crud").description("The CRUD resource")),
                        responseFields(subsectionWithPath("_links")
                                .description("Links to other resources")),
                        responseHeaders(headerWithName("Content-Type")
                                .description("The Content-Type of the payload"))));
    }

    @Test
    public void crudCreateExample() throws Exception {
        Map<String, Object> crud = new HashMap<>();
        crud.put("id", 1L);
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.yeob32.com/");

        this.mockMvc.perform(post("/crud").contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(crud)))
                .andExpect(status().isCreated())
                .andDo(document("create-crud-example",
                        requestFields(fieldWithPath("id").description("The id of the input"),
                                fieldWithPath("title").description("The title of the input"),
                                fieldWithPath("body").description("The body of the input")
                        )));
    }

    @Test
    public void crudDeleteExample() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/crud/{id}", 10))
                .andExpect(status().isOk())
                .andDo(document("crud-delete-example", pathParameters(parameterWithName("id").description("The id of the input to delete"))));
    }

    @Test
    public void crudPatchExample() throws Exception {

        Map<String, String> tag = new HashMap<>();
        tag.put("name", "PATCH");

        String tagLocation = this.mockMvc.perform(patch("/crud/{id}", 10).contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(tag)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model Patch");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        this.mockMvc.perform(patch("/crud/{id}", 10).contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(crud)))
                .andExpect(status().isOk());
    }

    @Test
    public void crudPutExample() throws Exception {
        Map<String, String> tag = new HashMap<>();
        tag.put("name", "PUT");

        String tagLocation = this.mockMvc.perform(put("/crud/{id}", 10).contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(tag)))
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        this.mockMvc.perform(put("/crud/{id}", 10).contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(crud)))
                .andExpect(status().isAccepted());
    }
}
