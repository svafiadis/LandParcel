package landclan.com.landparcel.repo;

import landclan.com.landparcel.domain.LandParcel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandParcelRepository extends JpaRepository<LandParcel, Long> {
    
}
