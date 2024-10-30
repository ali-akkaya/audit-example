package me.aliakkaya.auditexample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "person")
@Data()
public class Person extends BaseEntity{

    private String name;
    private String surname;

}
