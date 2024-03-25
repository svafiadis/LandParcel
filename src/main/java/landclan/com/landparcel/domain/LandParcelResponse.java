package landclan.com.landparcel.domain;

import java.util.List;

public class LandParcelResponse {
    private List<LandParcel> landParcels;

    public LandParcelResponse() {
    }

    public LandParcelResponse(List<LandParcel> landParcels) {
        this.landParcels = landParcels;
    }

    public List<LandParcel> getLandParcels() {
        return landParcels;
    }

    public void setLandParcels(List<LandParcel> landParcels) {
        this.landParcels = landParcels;
    }
    
    
}
