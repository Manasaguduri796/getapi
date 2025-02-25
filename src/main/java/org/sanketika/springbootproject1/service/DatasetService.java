package org.sanketika.springbootproject1.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.sanketika.springbootproject1.entity.Dataset;
import org.sanketika.springbootproject1.entity.Status;
import org.sanketika.springbootproject1.repository.DatasetRepository;
import org.sanketika.springbootproject1.response.DatasetResponse;
import org.sanketika.springbootproject1.response.ResponsePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DatasetService {
    @Autowired
    private final DatasetRepository datasetRepository;


    public DatasetService(DatasetRepository datasetRepository) {
        this.datasetRepository = datasetRepository;


    }
//GETALL
    public ResponseEntity<?> getAllDataset() {

        List<Dataset> datasetList = datasetRepository.findAll();
        if (datasetList.isEmpty()) {
            return (ResponseEntity.ok(DatasetResponse.createResponse("Success", HttpStatus.OK, "no dataset found", Collections.emptyList())));
        }
        return (ResponseEntity.ok(DatasetResponse.createResponse("Success", HttpStatus.OK, null, datasetList)));
    }
//GETBYID
    public ResponseEntity<?> getById(String id) {

        Optional<Dataset> dataset = datasetRepository.findById(id);
        if (dataset.isPresent()) {
            return (ResponseEntity.ok(DatasetResponse.createResponse("Success", HttpStatus.OK, null, datasetRepository.findById(id))));
        } else {
            return (ResponseEntity.status(HttpStatus.NOT_FOUND).body(DatasetResponse.createResponse("Failure", HttpStatus.NOT_FOUND, "Requested dataset id is not found", null)));
        }


    }
//GETBYSTATUS
    public ResponseEntity<?> getByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(DatasetResponse.createResponse("failure", HttpStatus.BAD_REQUEST, "status parameter is required", null));
        }
        try {
            Status enumStatus = Status.valueOf(status.toUpperCase());
            List<Dataset> datasetList = datasetRepository.findByStatus(enumStatus);
            return ResponseEntity.ok(DatasetResponse.createResponse(
                    "success", HttpStatus.OK, null, datasetList));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(DatasetResponse.createResponse(
                    "failure", HttpStatus.BAD_REQUEST, "Invalid status. Allowed: LIVE, DRAFT, RETIRED", null));
        }

    }
//CREATE
    public ResponseEntity<?> createDataset(String datasetJson) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Dataset dataset = mapper.readValue(datasetJson, Dataset.class);

            if (dataset.getId() == null || dataset.getId().isEmpty()) {
                return ResponseEntity.badRequest().body(DatasetResponse.createResponse(
                        "Fail", HttpStatus.BAD_REQUEST, "Primary key 'id' is required", null));
            }
            if (dataset.getDataSchema() == null || dataset.getDataSchema().isEmpty()) {
                return ResponseEntity.badRequest().body(DatasetResponse.createResponse(
                        "Fail", HttpStatus.BAD_REQUEST, "Data schema is required", null));
            }
            if (dataset.getRouterConfig() == null || dataset.getRouterConfig().isEmpty()) {
                return ResponseEntity.badRequest().body(DatasetResponse.createResponse(
                        "Fail", HttpStatus.BAD_REQUEST, "Router config is required", null));
            }
            if(datasetRepository.existsById(dataset.getId())){
                throw new DuplicateKeyException(dataset.getId());
            }
            dataset.setStatus(Status.valueOf("DRAFT"));
            dataset.setCreatedBy("SYSTEM");
            dataset.setUpdatedBy("SYSTEM");
            dataset.setCreatedByDate(LocalDateTime.now());
            dataset.setUpdatedByDate(LocalDateTime.now());
            Dataset savedDataset = datasetRepository.save(dataset);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePost.createResponses(
                    "Success", HttpStatus.CREATED, "Dataset saved successfully with ID: " + savedDataset.getId(), savedDataset));


        } catch(DuplicateKeyException d){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(DatasetResponse.createResponse("Fail", HttpStatus.CONFLICT,  "requested id is already existed",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DatasetResponse.createResponse(
                    "Fail", HttpStatus.INTERNAL_SERVER_ERROR, "Error processing request: ", null));
        }
    }

//UPDATE
    public ResponseEntity<?> updateDatasetById(String id,Dataset updateDataset) {
        try {
            Optional<Dataset> datasetExi = datasetRepository.findById(id);
            if (!datasetExi.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DatasetResponse.createResponse("Fail", HttpStatus.BAD_REQUEST, "requested dataset id not found ", null));
            }

            Dataset existingDataset = datasetExi.get();

            if (updateDataset.getDataSchema()== null || existingDataset.getDataSchema().values().isEmpty()) {
                return ResponseEntity.badRequest().body(DatasetResponse.createResponse("fail", HttpStatus.BAD_REQUEST, "dataSchema is required", null));
            }
            if (updateDataset.getRouterConfig() == null || existingDataset.getRouterConfig().values().isEmpty()) {
                return ResponseEntity.badRequest().body(DatasetResponse.createResponse("fail", HttpStatus.BAD_REQUEST, "routerConfig is required", null));
            }

          //  existingDataset.setStatus(Status.valueOf("DRAFT"));
            existingDataset.setCreatedBy("SYSTEM");
            existingDataset.setUpdatedBy("SYSTEM");
            existingDataset.setCreatedByDate(LocalDateTime.now());

            if(updateDataset.getDataSchema()!=null){
                existingDataset.setDataSchema(updateDataset.getDataSchema());
            }
            if(updateDataset.getRouterConfig()!=null){
                existingDataset.setRouterConfig(updateDataset.getRouterConfig());
            }
            if(updateDataset.getStatus()!=null){
                existingDataset.setStatus(updateDataset.getStatus());
            }

            Dataset updateDatasets = datasetRepository.save(existingDataset);
            return ResponseEntity.ok().body(ResponsePost.createResponses("Success", HttpStatus.OK, "Dataset with id is updated sucessfully", updateDatasets));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DatasetResponse.createResponse("fail", HttpStatus.INTERNAL_SERVER_ERROR, "An error occured", null));
        }
    }
//DELETEBYID
    public ResponseEntity<?> deletedDatasetById(String id) {
        Optional<Dataset> datasetOpt = datasetRepository.findById(id);
        if (!datasetOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    DatasetResponse.createResponse("Fail", HttpStatus.NOT_FOUND, "Dataset not found", null));
        }
        datasetRepository.deleteById(id);
        return ResponseEntity.ok(ResponsePost.createResponses("Success", HttpStatus.OK, "Dataset id is deleted successfully", null));

    }
}








