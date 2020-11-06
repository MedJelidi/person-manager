package de.tekup.rest.data.services;

import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.PersonEntity;

import java.util.List;

public interface PersonService {
    PersonEntity createPersonEntity(PersonEntity entity);
    List<PersonEntity> getAllPersonEntities();
    PersonEntity getPersonEntityById(long id);
    PersonEntity modifyPersonEntity(long id, PersonEntity entity);
    PersonEntity deletePersonEntity(long id);
    List<AddressEntity> getAllAddressEntities();
    public List<PersonEntity> getAllPersonByPhoneOperator(String operator);
}
