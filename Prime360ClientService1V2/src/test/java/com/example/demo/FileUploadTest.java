package com.example.demo;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.psx.prime360ClientService.config.Prime360RequestPosting;
import com.psx.prime360ClientService.resourcehandler.RequestPostingResourceHandler;
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(RequestPostingResourceHandler.class)
@AutoConfigureRestDocs("target/generated-snippets")
@ContextConfiguration(classes = {Prime360RequestPosting.class})
public class FileUploadTest {
    private InputStream is;
    private MockMvc mockMvc;
    
    @Spy
    @InjectMocks
    private RequestPostingResourceHandler controller = new RequestPostingResourceHandler();
    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        is = controller.getClass().getClassLoader().getResourceAsStream("");
    }
//    @Test
//    public void handleFileUpload() throws Exception {
//        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "FinalEcommerce.csv", "multipart/form-data", is);
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/post").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
//                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
//        Assert.assertEquals(200, result.getResponse().getStatus());
//        Assert.assertNotNull(result.getResponse().getContentAsString());
//        Assert.assertEquals("You successfully uploaded FinalEcommerce.csv!", result.getResponse().getContentAsString());
//    }
//    @Test
//    public void processDataAndGetPreviewContent() throws Exception
//    {
//    	 MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "list", "multipart/form-data", is);
//         MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/getCsv/FinalEcommerce.csv").contentType(MediaType.MULTIPART_FORM_DATA))
//                 .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
//         Assert.assertEquals(200, result.getResponse().getStatus());
//         Assert.assertNotNull(result.getResponse().getContentAsString());
//         Assert.assertEquals("[]", result.getResponse().getContentAsString());
//    }
//    @Test
//    public void getFileData() throws Exception
//    {
//    	MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "list", "multipart/form-data", is);
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/getFileData").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
//                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
//        Assert.assertEquals(200, result.getResponse().getStatus());
//        Assert.assertNotNull(result.getResponse().getContentAsString());
//        Assert.assertEquals("[]", result.getResponse().getContentAsString());
//    }
    
    
    @Test
	public void getRelationalProfiles() throws Exception {
    	MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "list", "multipart/form-data", is);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/requestPosting/getRelationalProfiles").contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertNotNull(result.getResponse().getContentAsString());
        Assert.assertEquals("[]", result.getResponse().getContentAsString());
    }
    
}
