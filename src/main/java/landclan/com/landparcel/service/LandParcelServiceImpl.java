package landclan.com.landparcel.service;

import java.util.Optional;
import landclan.com.landparcel.domain.LandParcel;
import landclan.com.landparcel.domain.LandParcelResponse;
import landclan.com.landparcel.exception.LandParcelNotFoundException;
import landclan.com.landparcel.repo.LandParcelRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Methods for CRUD operations for Land Parcels.
 * @author Stefanos Vafiadis
 * @since 1.0
 */
@Service
public class LandParcelServiceImpl implements LandParcelService {
    
    @Autowired
    LandParcelRepository landParcelRepository;
    
    @Override
    public LandParcelResponse getLandParcels() {
        LandParcelResponse response = new LandParcelResponse();
        response.setLandParcels(landParcelRepository.findAll());
        return response;
    }
    
    @Override
    public Optional<LandParcel> getLandParcel(Long id) {
        return landParcelRepository.findById(id);
    }
    
    /**
     * 
     * @param newLandParcel should have all properties assigned as all the db table has been set to NOT NULL for all fields.
     * @return The Land Parcel created in the database.
     */
    @Override
    public LandParcel saveLandParcel(LandParcel newLandParcel) {
        LandParcel landParcel = new LandParcel();
        BeanUtils.copyProperties(newLandParcel, landParcel);
        return landParcelRepository.save(landParcel);
    }
    
    /**
     * 
     * @param updatedLandParcel should have all properties assigned.
     * @return The Land Parcel updated in the database.
     * @throws LandParcelNotFoundException If the objectID is not found in the database.
     */
    @Override
    public LandParcel updateLandParcel(LandParcel updatedLandParcel) {
        Optional<LandParcel> existingLandParcel = getLandParcel(updatedLandParcel.getObjectID());
        
        if (existingLandParcel.isPresent()) {
            LandParcel landParcel = existingLandParcel.get();
            landParcel.setName(updatedLandParcel.getName());
            landParcel.setStatus(updatedLandParcel.getStatus());
            landParcel.setArea(updatedLandParcel.getArea());
            landParcel.setConstraints(updatedLandParcel.isConstraints());
            return landParcelRepository.save(landParcel);
        } else {
            throw new LandParcelNotFoundException("Land Parcel with object ID:"+updatedLandParcel.getObjectID()+" not found.");
        }
    }
    
    /**
     * 
     * @param id should be set to the objectID of the Land Parcel to be deleted.
     * @throws LandParcelNotFoundException If the objectID is not found in the database.
     */
    @Override
    public void deleteLandParcel(Long id) {
        Optional<LandParcel> existingLandParcel = getLandParcel(id);
        
        if (existingLandParcel.isPresent()) {
            landParcelRepository.deleteById(id);
        } else {
            throw new LandParcelNotFoundException("Land Parcel with object ID:"+id+" not found for deletion.");
        }
    };
}
