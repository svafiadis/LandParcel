package landclan.com.landparcel.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import landclan.com.landparcel.domain.LandParcel;
import landclan.com.landparcel.domain.LandParcelResponse;
import landclan.com.landparcel.domain.Status;
import landclan.com.landparcel.service.LandParcelService;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = LandParcelController.class)
public class LandParcelControllerTests {
    
    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    LandParcelService landParcelService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private LandParcel landParcel;

    @BeforeEach
    public void init() {
        landParcel = new LandParcel(1L, "FirstParcel", Status.SAVED, 9.5, true);
    }
    
    @Test
    @DisplayName("Ensure the correct number of Land Parcels are returned.")
    public void testGetLandParcels() throws Exception {
        LandParcelResponse response = new LandParcelResponse();
        List<LandParcel> landParcels = new ArrayList<>();
        LandParcel landParcel2 = new LandParcel(2L, "SecondParcel", Status.SHORT_LISTED, 4.0, false);
        landParcels.add(landParcel);
        landParcels.add(landParcel2);
        response.setLandParcels(landParcels);
        when(landParcelService.getLandParcels()).thenReturn(response);
        
        ResultActions result = mockMvc.perform(get("/api/landParcel"));   
                
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.landParcels.size()", is(response.getLandParcels().size())));       
    }

    @Test
    @DisplayName("Ensure Land Parcel is corrected displayed.")
    public void testGetLandParcel() throws Exception {
        Optional<LandParcel> returnedLandParcel = Optional.of(landParcel);
        
        when(landParcelService.getLandParcel(1L)).thenReturn(returnedLandParcel);
        
        ResultActions result = mockMvc.perform(get("/api/landParcel/1"));   
                
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.objectID", is((int) landParcel.getObjectID())))
                .andExpect(jsonPath("$.name", is(landParcel.getName())))
                .andExpect(jsonPath("$.status", is(landParcel.getStatus().toString())))
                .andExpect(jsonPath("$.area", is(landParcel.getArea())))
                .andExpect(jsonPath("$.constraints", is(landParcel.isConstraints())));         
    }
    
    @Test
    @DisplayName("Ensure the correct http code is returned when a land parcel cannot be found.")
    public void testGetLandParcelWhichDoesNotExist() throws Exception {
        when(landParcelService.getLandParcel(3L)).thenReturn(Optional.empty());
        
        ResultActions result = mockMvc.perform(get("/api/landParcel/3"));   
                
        result.andExpect(status().isNoContent());       
    }
    
    @Test
    @DisplayName("Ensure the correct http code is returned when a land parcel is created and the correct values are displayed.")
    public void testCreateLandParcel() throws Exception {
        given(landParcelService.saveLandParcel(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));
        
        ResultActions result = mockMvc.perform(post("/api/landParcel")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(objectMapper.writeValueAsString(landParcel)));
                
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.objectID", is((int) landParcel.getObjectID())))
                .andExpect(jsonPath("$.name", is(landParcel.getName())))
                .andExpect(jsonPath("$.status", is(landParcel.getStatus().toString())))
                .andExpect(jsonPath("$.area", is(landParcel.getArea())))
                .andExpect(jsonPath("$.constraints", is(landParcel.isConstraints())));      
    }
    
    @Test
    @DisplayName("Ensure the correct http code is returned when a land parcel is updated and the correct values are displayed.")
    public void testUpdateLandParcel() throws Exception {
        given(landParcelService.updateLandParcel(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions result = mockMvc.perform(put("/api/landParcel")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(objectMapper.writeValueAsString(landParcel)));
                
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.objectID", is((int) landParcel.getObjectID())))
                .andExpect(jsonPath("$.name", is(landParcel.getName())))
                .andExpect(jsonPath("$.status", is(landParcel.getStatus().toString())))
                .andExpect(jsonPath("$.area", is(landParcel.getArea())))
                .andExpect(jsonPath("$.constraints", is(landParcel.isConstraints()))); 
    }
    
    @Test
    @DisplayName("Ensure the correct http code is returned when a land parcel is deleted.")
    public void testDeleteLandParcel() throws Exception {
        doNothing().when(landParcelService).deleteLandParcel(landParcel.getObjectID());
        
        ResultActions result = mockMvc.perform(delete("/api/landParcel/1"));
        
        result.andExpect(status().isOk());
    }
}
