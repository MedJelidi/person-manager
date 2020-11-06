package de.tekup.rest.data.services;

import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.GamesEntity;
import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.models.TelephoneNumberEntity;
import de.tekup.rest.data.repositories.AddressRepository;
import de.tekup.rest.data.repositories.GameRepository;
import de.tekup.rest.data.repositories.PersonRepository;
import de.tekup.rest.data.repositories.TelephoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepo;
    private final AddressRepository addressRepository;
    private final TelephoneRepository telephoneRepository;
    private final GameRepository gameRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepo,
                             AddressRepository addressRepository,
                             TelephoneRepository telephoneRepository,
                             GameRepository gameRepository) {
        this.personRepo = personRepo;
        this.addressRepository = addressRepository;
        this.telephoneRepository = telephoneRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public PersonEntity createPersonEntity(PersonEntity entity) {
        // Saving Address
        AddressEntity addressEntity = entity.getAddress();
        AddressEntity addressInBase = this.addressRepository.save(addressEntity);
        //saving person
        entity.setAddress(addressInBase);
        PersonEntity newEntity = this.personRepo.save(entity);
        //saving phoneNumbers
        for (TelephoneNumberEntity phone : entity.getPhones()) {
            phone.setPerson(newEntity);
            this.telephoneRepository.save(phone);
        }
        //saving games
        List<PersonEntity> persons;
        for (GamesEntity game : entity.getGames()) {
            if (game.getPersons() != null) {
                persons = game.getPersons();
            } else {
                persons = new ArrayList<>();
            }
            persons.add(newEntity);
            game.setPersons(persons);
            this.gameRepository.save(game);

        }
        return newEntity;
    }

    @Override
    public List<PersonEntity> getAllPersonEntities() {
        return this.personRepo.findAll();
    }

    @Override
    public PersonEntity getPersonEntityById(long id) {
        Optional<PersonEntity> optional = this.personRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NoSuchElementException("Person with this id is not found");
    }

    @Override
    public PersonEntity modifyPersonEntity(long id, PersonEntity newEntity) {
        Optional<PersonEntity> optional = this.personRepo.findById(id);

        if (optional.isEmpty())
            throw new NoSuchElementException("Person with this id is not found");

        PersonEntity oldEntity = optional.get();

        if (newEntity.getName() != null)
            oldEntity.setName(newEntity.getName());

        if (newEntity.getDateOfBirth() != null)
            oldEntity.setDateOfBirth(newEntity.getDateOfBirth());

        AddressEntity oldAddress = oldEntity.getAddress();
        AddressEntity newAddress = newEntity.getAddress();

        if (newAddress != null) {
            if (newAddress.getCity() != null)
                oldAddress.setCity(newAddress.getCity());

            if (newAddress.getNumber() != 0)
                oldAddress.setNumber(newAddress.getNumber());

            if (newAddress.getStreet() != null)
                oldAddress.setStreet(newAddress.getStreet());
        }

        List<TelephoneNumberEntity> oldPhones= oldEntity.getPhones();
        List<TelephoneNumberEntity> newPhones= newEntity.getPhones();

        if (newPhones != null) {
            for (TelephoneNumberEntity newPhone : newPhones) {
                for (TelephoneNumberEntity oldPhone : oldPhones) {
                    if (oldPhone.getId() == newPhone.getId()) {
                        if (newPhone.getNumber() != null)
                            oldPhone.setNumber(newPhone.getNumber());
                        if (newPhone.getOperator() != null)
                            oldPhone.setOperator(newPhone.getOperator());
                        // break over Old loop
                        break;
                    }
                }
            }
        }

        return this.personRepo.save(oldEntity);
    }

    @Override
    public PersonEntity deletePersonEntity(long id) {
        PersonEntity entity = this.getPersonEntityById(id);

        this.personRepo.deleteById(id);

        return entity;
    }

    @Override
    public List<AddressEntity> getAllAddressEntities() {
        return this.addressRepository.findAll();
    }

    @Override
    public List<PersonEntity> getAllPersonByPhoneOperator(String operator) {
        List<PersonEntity> persons = this.personRepo.findAll();
        List<PersonEntity> personsWithOperator = new ArrayList<>();
        for (PersonEntity person : persons) {
            for (TelephoneNumberEntity phone : person.getPhones()) {
                if(phone.getOperator().equalsIgnoreCase(operator)) {
                    personsWithOperator.add(person);
                    break;
                }
            }
        }

        return personsWithOperator;
    }
}
