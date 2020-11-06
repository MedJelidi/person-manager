package de.tekup.rest.data.repositories;

import de.tekup.rest.data.models.TelephoneNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelephoneRepository extends JpaRepository<TelephoneNumberEntity, Integer> {

}
