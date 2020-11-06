package de.tekup.rest.data.repositories;

import de.tekup.rest.data.models.GamesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GamesEntity, Integer> {

}
