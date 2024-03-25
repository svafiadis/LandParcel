package landclan.com.landparcel.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "landParcel")
public class LandParcel implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private long objectID;
    
    @Column
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column
    private Status status;
    
    @Column
    private double area;
    
    @Column
    private boolean constraints;
    
    public LandParcel() {}

    public LandParcel(long objectID, String name, Status status, double area, boolean constraints) {
        this.objectID = objectID;
        this.name = name;
        this.status = status;
        this.area = area;
        this.constraints = constraints;
    }

    public long getObjectID() {
        return objectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public boolean isConstraints() {
        return constraints;
    }

    public void setConstraints(boolean constraints) {
        this.constraints = constraints;
    }
    
    @Override
	public String toString() {
            return "LandParcel("+
                        "objectID="+objectID+", "+
                        "name="+name+", "+
                        "status="+status+", "+
                        "area="+area+", "+
                        "constraints="+constraints+")";
        }
}
