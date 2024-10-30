package me.aliakkaya.auditexample.repository;

import me.aliakkaya.auditexample.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRepository extends CrudRepository<Person, Long> {
}
