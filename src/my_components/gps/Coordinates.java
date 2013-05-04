package my_components.gps;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table COORDINATES.
 */
public class Coordinates extends com.example.my_fragment.ComponentSimpleModel  {

    private Long id;
    private Long targetId;
    private double latitude;
    private double longitude;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;

    public Coordinates() {
    }

    public Coordinates(Long id) {
        this.id = id;
    }

    public Coordinates(Long id, Long targetId, double latitude, double longitude, String addressLine1, String addressLine2, String addressLine3) {
        this.id = id;
        this.targetId = targetId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

}
