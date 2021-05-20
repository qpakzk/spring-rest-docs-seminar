package co.kr.tmaxai.product.controller;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        ProductDto productDto = ProductDto.builder()
                .name("HyperChatbot")
                .description("A brilliant chatbot platform")
                .quantity(10)
                .build();

        Long productId = 1L;
        doReturn(productId).when(productService).createProduct(any());

        //when
        //then
        String productDtoToJsonString = objectMapper.writeValueAsString(productDto);
        mockMvc.perform(post("/").content(productDtoToJsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("product-create",
                requestFields(
                        fieldWithPath("name").type(String.class).description("상품명"),
                        fieldWithPath("description").type(String.class).description("상품 설명"),
                        fieldWithPath("quantity").type(Integer.class).description("상품 수량")
                )));
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void getProduct() {
    }
}