package org.sanketika.springbootproject1.controller;


import org.sanketika.springbootproject1.entity.Dataset;
import org.sanketika.springbootproject1.entity.Status;
import org.sanketika.springbootproject1.repository.DatasetRepository;
import org.sanketika.springbootproject1.response.DatasetResponse;
import org.sanketika.springbootproject1.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@Validated
@RequestMapping("/datasetapis")
public class DatasetController {
    private DatasetService datasetService;

    public DatasetController(DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllDataset() {
        return datasetService.getAllDataset();
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getDatasetById(@PathVariable String id) {
        return datasetService.getById(id);
    }


    @GetMapping("/getByStatus")
    public ResponseEntity<?> getByDatasetStatus(@RequestParam(name = "status", required = false) String status) {
        return datasetService.getByStatus(status);
    }

    @PostMapping("/create")
    public ResponseEntity<?> CreateDataset(@RequestBody String  datasetJson) {
        return datasetService.createDataset(datasetJson);
    }

  @PutMapping("/update/{id}")
     public ResponseEntity<?> UpdateDatasetById(@PathVariable String id ,@RequestBody Dataset updateDataset){
   return datasetService.updateDatasetById(id,updateDataset);
  }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletedDatasetById(@PathVariable String id) {
        return datasetService.deletedDatasetById(id);
    }
}




