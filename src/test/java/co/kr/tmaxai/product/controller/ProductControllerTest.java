package co.kr.tmaxai.product.controller;

import co.kr.tmaxai.product.domain.Product;
import co.kr.tmaxai.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ExtendWith(RestDocumentationExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new ShallowEtagHeaderFilter())
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }
    @Test
    void testCreateProduct() throws Exception {
        //given
        Product product = Product.builder()
                .id(1L)
                .name("HyperChatbot")
                .description("A brilliant chatbot platform")
                .quantity(10)
                .build();

        doReturn(1L).when(productService).createProduct(any());

        //when
        //then
        String productDtoToJsonString = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/").content(productDtoToJsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("product-create",
                requestFields(
                        fieldWithPath("id").type(Long.class).description("상품 ID"),
                        fieldWithPath("name").type(String.class).description("상품명"),
                        fieldWithPath("description").type(String.class).description("상품 설명"),
                        fieldWithPath("quantity").type(Integer.class).description("상품 수량")
                )));
    }

    @Test
    void testRetrieveAllProducts() throws Exception {
        //given
        Product product1 = Product.builder()
                .id(1L)
                .name("HyperChatbot")
                .description("A brilliant chatbot platform")
                .quantity(10)
                .build();
        Product productDto2 = Product.builder()
                .id(2L)
                .name("Tibero")
                .description("A brilliant DBMS")
                .quantity(20)
                .build();

        List<Product> products = Arrays.asList(product1, productDto2);
        doReturn(products).when(productService).retrieveAllProducts();

        //when
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("HyperChatbot")))
                .andExpect(jsonPath("$[0].description", is("A brilliant chatbot platform")))
                .andExpect(jsonPath("$[0].quantity", is(10)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Tibero")))
                .andExpect(jsonPath("$[1].description", is("A brilliant DBMS")))
                .andExpect(jsonPath("$[1].quantity", is(20)))
                .andDo(document("product-retrieve-all"));
    }

    @Test
    void testRetrieveProduct() throws Exception {
        Product product = Product.builder()
                .id(1L)
                .name("HyperChatbot")
                .description("A brilliant chatbot platform")
                .quantity(10)
                .build();

        doReturn(product).when(productService).retrieveProduct(1L);

        //when
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("HyperChatbot")))
                .andExpect(jsonPath("$.description", is("A brilliant chatbot platform")))
                .andExpect(jsonPath("$.quantity", is(10)))
                .andDo(document("product-retrieve",
                    pathParameters(
                            parameterWithName("id").description("상품 ID")
                    )
                ));
    }
}