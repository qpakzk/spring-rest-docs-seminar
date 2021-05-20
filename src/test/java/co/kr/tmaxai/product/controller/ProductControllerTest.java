package co.kr.tmaxai.product.controller;

import co.kr.tmaxai.product.domain.Product;
import co.kr.tmaxai.product.dto.ProductDto;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ExtendWith(RestDocumentationExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new ShallowEtagHeaderFilter())
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }
    @Test
    void testCreateProduct() throws Exception {
        //given
        Long id = 1L;
        String name = "HyperChatbot";
        String description = "A brilliant chatbot platform";
        int quantity = 10;
        ProductDto productDto = ProductDto.builder()
                .name(name)
                .description(description)
                .quantity(quantity)
                .build();

        doReturn(id).when(productService).createProduct(any(Product.class));

        //when
        //then
        String productDtoToJsonString = objectMapper.writeValueAsString(productDto);
        mockMvc.perform(post("/product/new").content(productDtoToJsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(id.intValue())))
                .andDo(document("product/create",
                requestFields(
                        fieldWithPath("name").type(String.class).description("상품명"),
                        fieldWithPath("description").type(String.class).description("상품 설명"),
                        fieldWithPath("quantity").type(Integer.class).description("상품 수량")
                ),
                responseFields(
                        fieldWithPath("id").type(Long.class).description("상품 ID")
                )));
    }

    @Test
    void testRetrieveAllProducts() throws Exception {
        //given
        Long id = 1L;
        String name = "HyperChatbot";
        String description = "A brilliant chatbot platform";
        int quantity = 10;
        Product product = Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .quantity(quantity)
                .build();

        Long id2 = 2L;
        String name2 = "Tibero";
        String description2 = "A brilliant DBMS";
        int quantity2 = 20;
        Product product2 = Product.builder()
                .id(id2)
                .name(name2)
                .description(description2)
                .quantity(quantity2)
                .build();

        List<Product> products = Arrays.asList(product, product2);
        doReturn(products).when(productService).retrieveAllProducts();

        //when
        //then
        mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(name)))
                .andExpect(jsonPath("$[0].description", is(description)))
                .andExpect(jsonPath("$[0].quantity", is(quantity)))
                .andExpect(jsonPath("$[1].name", is(name2)))
                .andExpect(jsonPath("$[1].description", is(description2)))
                .andExpect(jsonPath("$[1].quantity", is(quantity2)))
                .andDo(document("product/retrieve-all",
                        responseFields(
                                fieldWithPath("[].name").type(String.class).description("상품명"),
                                fieldWithPath("[].description").type(String.class).description("상품 설명"),
                                fieldWithPath("[].quantity").type(Integer.class).description("상품 수량")
                        )
                ));
    }

    @Test
    void testRetrieveProduct() throws Exception {
        Long id = 1L;
        String name = "HyperChatbot";
        String description = "A brilliant chatbot platform";
        int quantity = 10;
        Product product = Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .quantity(quantity)
                .build();

        doReturn(product).when(productService).retrieveProduct(id);

        //when
        //then
        mockMvc.perform(get("/product/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.description", is(description)))
                .andExpect(jsonPath("$.quantity", is(quantity)))
                .andDo(document("product/retrieve",
                    pathParameters(
                            parameterWithName("id").description("상품 ID")
                    ),
                    responseFields(
                            fieldWithPath("id").type(Long.class).description("상품 ID"),
                            fieldWithPath("name").type(String.class).description("상품명"),
                            fieldWithPath("description").type(String.class).description("상품 설명"),
                            fieldWithPath("quantity").type(Integer.class).description("상품 수량")
                    )
                ));
    }
}