package landclan.com.landparcel.controller;

import java.util.Optional;
import landclan.com.landparcel.domain.LandParcel;
import landclan.com.landparcel.domain.LandParcelResponse;
import landclan.com.landparcel.exception.LandParcelNotFoundException;
import landclan.com.landparcel.service.LandParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contains a list of all API requests available for CRUD operations on Land Parcels.
 * @author Stefanos Vafiadis
 */
@RestController
@RequestMapping("/api")
public class LandParcelController {
    
    @Autowired
    LandParcelService landParcelService;
    
    /**
     * GET /api/landParcel
     * @return Returns all Land Parcels found. 
     * <table>
     * <tr>
     * <th>
     *  HTTP CODE
     * </th>
     * <th>
     *  HTTP Status
     * </th>
     * <th>
     *  Description
     * </th>
     * </tr>
     * <tr>
     * <td>
     * 200
     * </td>
     * <td>
     * OK
     * </td>
     * <td>
     * The request was successful and Land Parcels should be returned in JSON within an Array of name landParcels.
     * </td>
     * </tr>
     * <tr>
     * <td>
     * 204
     * </td>
     * <td>
     * No Content
     * </td>
     * <td>
     * The request was successful but there are no Land Parcels to display currently.
     * </td>
     * </tr>
     * <tr>
     * <td>
     * 500
     * </td>
     * <td>
     * Internal Server Error
     * </td>
     * <td>
     * The request was unsuccessful.
     * </td>
     * </tr>
     * </table>
     */
    @GetMapping("/landParcel")
    public ResponseEntity<LandParcelResponse> getAllLandParcels() {
        try {
            LandParcelResponse landParcels = landParcelService.getLandParcels();

            if (landParcels.getLandParcels().isEmpty()) {
              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(landParcels, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * GET /api/landParcel/{objectId}
     * @param objectId The integer value of the ObjectID of the Land Parcel to be found.
     * @return Returns a specific Land Parcel based on the ObjectID provided. 
     * <table>
     * <tr>
     * <th>
     *  HTTP CODE
     * </th>
     * <th>
     *  HTTP Status
     * </th>
     * <th>
     *  Description
     * </th>
     * </tr>
     * <tr>
     * <td>
     * 200
     * </td>
     * <td>
     * OK
     * </td>
     * <td>
     * The request was successful and a land parcel should be return in JSON form.
     * </td>
     * </tr>
     * <tr>
     * <td>
     * 204
     * </td>
     * <td>
     * No Content
     * </td>
     * <td>
     * The request was successful but there are no Land Parcels matching the objectID provided.
     * </td>
     * </tr>
     * <tr>
     * <td>
     * 500
     * </td>
     * <td>
     * Internal Server Error
     * </td>
     * <td>
     * The request was unsuccessful.
     * </td>
     * </tr>
     * </table>
     */
    @GetMapping("/landParcel/{objectId}")
    public ResponseEntity<LandParcel> getLandParcel(@PathVariable long objectId) {
        try {
            Optional<LandParcel> landParcel = landParcelService.getLandParcel(objectId);

            if (landParcel.isEmpty()) {
              return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(landParcel.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * POST /api/landParcel
     * A land parcel is JSON format must be provided an example shown below
     * {
     *      "name": "Test",
     *      "status": "SAVED",
     *      "area": 15.2,
     *      "constraints": false
     *  }
     * 
     * The field name accepts String values.
     * The field status accepts String values of type {"SAVED", "SHORT_LISTED", "UNDER_CONSIDERATION", "APPROVED"}
     * The field area accepts double values.
     * The field constrains accepts boolean values.
     * All fields must be correctly set otherwise the request will fail, any other fields defined will be ignored (including objectID which is set automatically by the database.
     * @param landParcel requires all fields described above to be created.
     * @return The land parcel created in JSON format.
     * <table>
     * <tr>
     * <th>
     *  HTTP CODE
     * </th>
     * <th>
     *  HTTP Status
     * </th>
     * <th>
     *  Description
     * </th>
     * </tr>
     * <tr>
     * <td>
     * 201
     * </td>
     * <td>
     * Created
     * </td>
     * <td>
     * The request was successful and a land parcel was created.
     * </td>
     * </tr>
     * <tr>
     * <td>
     * 400
     * </td>
     * <td>
     * Bad Request
     * </td>
     * <td>
     * The request was unsuccessful and a Land Parcel was not created.
     * </td>
     * </tr>
     * </table>
     */
    @PostMapping(value = "/landParcel")
    public ResponseEntity createLandParcel(@RequestBody LandParcel landParcel){
        try {
            LandParcel newLandParcel = landParcelService.saveLandParcel(landParcel);
            return new ResponseEntity<>(newLandParcel, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * PUT /api/landParcel
     * A land parcel is JSON format must be provided, an example shown below
     * {
     *      "objectID": 3,
     *      "name": "Test",
     *      "status": "SAVED",
     *      "area": 15.2,
     *      "constraints": false
     *  }
     * 
     * The field objectID accepts biginteger values.
     * The field name accepts String values.
     * The field status accepts String values of type {"SAVED", "SHORT_LISTED", "UNDER_CONSIDERATION", "APPROVED"}
     * The field area accepts double values.
     * The field constrains accepts boolean values.
     * All fields must be correctly set otherwise the request will fail, any other fields defined will be ignored.
     * The objectID field must correspond to an existing land parcel otherwise the request will fail with http code 404.
     * @param landParcel requires all fields described above to be updated correctly.
     * @return The land parcel updated in JSON format. 
     * <table>
     * <tr>
     * <th>
     *  HTTP CODE
     * </th>
     * <th>
     *  HTTP Status
     * </th>
     * <th>
     *  Description
     * </th>
     * </tr>
     * <tr>
     * <td>
     * 200
     * </td>
     * <td>
     * OK
     * </td>
     * <td>
     * The request was successful and a land parcel should be return in JSON form.
     * </td>
     * </tr>
     * <tr>
     * <td>
     * 400
     * </td>
     * <td>
     * Bad Request
     * </td>
     * <td>
     * The request was unsuccessful.
     * </td>
     * </tr>
     * <tr>
     * <td>
     * 404
     * </td>
     * <td>
     * Not Found
     * </td>
     * <td>
     * The land parcel with the objectID specified could not be found.
     * </td>
     * </tr>
     * </table>
     */
    @PutMapping(value = "/landParcel")
    public ResponseEntity updateLandParcel(@RequestBody LandParcel landParcel){
        try {
            LandParcel updatedLandParcel = landParcelService.updateLandParcel(landParcel);
            return new ResponseEntity<>(updatedLandParcel, HttpStatus.OK);
        } catch (LandParcelNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
     /**
     * DELETE /api/landParcel/{objectID}
     * To delete a land parcel based on the objectID provided.
     * If an objectID is provided for which a land parcel cannot be found the server will return a 404 http code.
     * @param objectId the object ID of the land parcel to be deleted.
     * @return 
     * <table>
     * <tr>
     * <th>
     *  HTTP CODE
     * </th>
     * <th>
     *  HTTP Status
     * </th>
     * <th>
     *  Description
     * </th>
     * </tr>
     * <tr>
     * <td>
     * 200
     * </td>
     * <td>
     * OK
     * </td>
     * <td>
     * The request was successful and a land parcel should be return in JSON form.
     * </td>
     * </tr>
     * <tr>
     * <td>
     * 404
     * </td>
     * <td>
     * Not Found
     * </td>
     * <td>
     * The land parcel with the objectID specified could not be found.
     * </td>
     * </tr>
     * <tr>
     * <td>
     * 500
     * </td>
     * <td>
     * Internal Server Error
     * </td>
     * <td>
     * The request was unsuccessful.
     * </td>
     * </tr>
     * </table>
     */
    @DeleteMapping("/landParcel/{objectId}")
    public ResponseEntity deleteLandParcel(@PathVariable Long objectId) {
        try {
            landParcelService.deleteLandParcel(objectId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LandParcelNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
