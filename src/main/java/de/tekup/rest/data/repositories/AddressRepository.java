package de.tekup.rest.data.repositories;

import de.tekup.rest.data.models.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {

}
