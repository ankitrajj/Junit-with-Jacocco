package in.ankit.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ankit.entities.CountryEntity;


public interface CountryRepository extends JpaRepository<CountryEntity, Serializable> {

}
