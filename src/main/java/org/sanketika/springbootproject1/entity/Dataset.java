package org.sanketika.springbootproject1.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name="table_1")
public class Dataset {
    @Id
    private String id;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String,Object> dataSchema;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "Jsonb")
    private Map<String,Object> routerConfig;
    @Enumerated(EnumType.STRING)
    private String Status;
    @Column(name="createdBy",nullable = false)
    private String createdBy;
    @Column(name = "updatedBy",nullable = false)
    private String updatedBy;
    @Column
    @CreationTimestamp//AUTOMATICALLY CREATE EXACT TIME
    private LocalDateTime createdByDate;
    @Column
    @UpdateTimestamp
    private LocalDateTime updatedByDate;

    public Dataset(String id, Map<String, Object> dataSchema, Map<String, Object> routerConfig,String status, String createdBy, String updatedBy, LocalDateTime createdByDate, LocalDateTime updatedByDate) {
        this.id = id;
        this.dataSchema = dataSchema;
        this.routerConfig = routerConfig;
        this.Status = status;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdByDate = createdByDate;
        this.updatedByDate = updatedByDate;
    }
    public Dataset(){

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getDataSchema() {
        return dataSchema;
    }

    public void setDataSchema(Map<String, Object> dataSchema) {
        this.dataSchema = dataSchema;
    }

    public Map<String, Object> getRouterConfig() {
        return routerConfig;
    }

    public void setRouterConfig(Map<String, Object> routerConfig) {
        this.routerConfig = routerConfig;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedByDate() {
        return createdByDate;
    }

    public void setCreatedByDate(LocalDateTime createdByDate) {
        this.createdByDate = createdByDate;
    }

    public LocalDateTime getUpdatedByDate() {
        return updatedByDate;
    }

    public void setUpdatedByDate(LocalDateTime updatedByDate) {
        this.updatedByDate = updatedByDate;
    }
}
