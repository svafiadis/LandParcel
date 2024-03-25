package landclan.com.landparcel.service;

import java.util.List;
import java.util.Optional;
import landclan.com.landparcel.domain.LandParcel;
import landclan.com.landparcel.domain.LandParcelResponse;
import landclan.com.landparcel.domain.Status;
import landclan.com.landparcel.exception.LandParcelNotFoundException;
import landclan.com.landparcel.repo.LandParcelRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LandParcelServiceTests {
    
    @Mock
    private LandParcelRepository landParcelRepository;
    
    @InjectMocks
    private LandParcelServiceImpl landParcelService;
    
    @Test
    @DisplayName("Ensure a non-empty set of land parcels are returned.")
    public void testGetLandParcels() {
        List<LandParcel> landParcels = Mockito.mock(List.class);
        
        when(landParcelRepository.findAll()).thenReturn(landParcels);
        
        LandParcelResponse response = landParcelService.getLandParcels();
        
        assertThat(response).isNotNull();
    }
    
    @Test
    @DisplayName("Ensure a land parcel is returned when the objectId is specified.")
    public void testGetLandParcelWhenExists() {
        long objectId = 1L;
        LandParcel landParcel = Mockito.mock(LandParcel.class);
        
        when(landParcelRepository.findById(objectId)).thenReturn(Optional.of(landParcel));
        
        Optional<LandParcel> result = landParcelService.getLandParcel(objectId);
        
        assertTrue(result.isPresent());
        assertSame(landParcel, result.get());
    }
    
    @Test
    @DisplayName("Ensure a land parcel is not returned when the objectId is specified.")
    public void testGetLandParcelWhenNotExists() {
        long objectId = 1L;
        
        when(landParcelRepository.findById(objectId)).thenReturn(Optional.ofNullable(null));
        
        Optional<LandParcel> result = landParcelService.getLandParcel(objectId);
        
        assertTrue(result.isEmpty());
    }
    
    @Test
    @DisplayName("Ensure a land parcel is created correctly.")
    public void testSaveLandParcel() {
        LandParcel landParcel = new LandParcel(1L, "FirstParcel", Status.SAVED, 9.5, true);
        
        when(landParcelRepository.save(Mockito.any(LandParcel.class))).thenReturn(landParcel);
        
        LandParcel savedLandParcel = landParcelService.saveLandParcel(landParcel);
        
        assertSame(savedLandParcel, landParcel);
    }
    
    @Test
    @DisplayName("Ensure a land parcel is updated correctly.")
    public void testUpdateLandParcel() {
        long objectId = 1L;
        LandParcel landParcel = new LandParcel(1L, "FirstParcel", Status.SAVED, 9.5, true);
        
        when(landParcelRepository.findById(objectId)).thenReturn(Optional.of(landParcel));
        when(landParcelRepository.save(landParcel)).thenReturn(landParcel);
        
        LandParcel updatedLandParcel = landParcelService.updateLandParcel(landParcel);
        
        assertThat(updatedLandParcel).isNotNull();
        assertSame(updatedLandParcel, landParcel);
    }
    
    @Test
    @DisplayName("Ensure the correct exception is thrown when a land parcel is attempted to be updated when it does not exist.")
    public void testUpdateLandParcelWhenNotExists() {
        long objectId = 1L;
        LandParcel landParcel = new LandParcel(1L, "FirstParcel", Status.SAVED, 9.5, true);
        
        when(landParcelRepository.findById(objectId)).thenReturn(Optional.ofNullable(null));
        
        Throwable exception = assertThrows(LandParcelNotFoundException.class, () -> landParcelService.updateLandParcel(landParcel));
        assertEquals("Land Parcel with object ID:"+objectId+" not found.", exception.getMessage());
    }
    
    @Test
    @DisplayName("Ensure a land parcel is correctly deleted.")
    public void testDeleteLandParcel() {
        long objectId = 1L;
        LandParcel landParcel = new LandParcel();
        
        when(landParcelRepository.findById(objectId)).thenReturn(Optional.of(landParcel));
        
        doNothing().when(landParcelRepository).deleteById(objectId);
        
        assertAll(() -> landParcelService.deleteLandParcel(objectId));
    }
    
    @Test
    @DisplayName("Ensure the correct exception is thrown when a land parcel is attempted to be deleted when it does not exist.")
    public void testDeleteLandParcelWhenNotExists() {
        long objectId = 1L;
        
        when(landParcelRepository.findById(objectId)).thenReturn(Optional.ofNullable(null));
        
        Throwable exception = assertThrows(LandParcelNotFoundException.class, () -> landParcelService.deleteLandParcel(objectId));
        assertEquals("Land Parcel with object ID:"+objectId+" not found for deletion.", exception.getMessage());
    }
}
