package landclan.com.landparcel.service;

import java.util.Optional;
import landclan.com.landparcel.domain.LandParcel;
import landclan.com.landparcel.domain.LandParcelResponse;

public interface LandParcelService {
    LandParcelResponse getLandParcels();
    Optional<LandParcel> getLandParcel(Long id);
    LandParcel saveLandParcel(LandParcel landParcel);
    LandParcel updateLandParcel(LandParcel landParcel);
    void deleteLandParcel(Long id);
}
