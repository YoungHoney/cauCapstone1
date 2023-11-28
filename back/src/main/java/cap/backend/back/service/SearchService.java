package cap.backend.back.service;

import cap.backend.back.domain.Clan;
import cap.backend.back.domain.Person;
import cap.backend.back.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {


    private final PersonRepository personRepository;

    public List<String> findPersonnamesByClan(Clan clan) {

        List<Person> plist=personRepository.findPersonsByClan(clan);

        return plist.stream()
                .map(Person::getName)
                .collect(Collectors.toList());
    }

    public List<Clan> findClansByLetter(char Letter) {
        return personRepository.findClansByLetter(Letter);

    }

    public boolean isPersoninDBByName(String name) {
        Person temp=personRepository.findPersonInDBByName(name);

        if(Objects.isNull(temp)) {
            return false;
        }
        else return true;

    }
    public Long findIdByName(String personname) {
        return personRepository.findPersonInDBByName(personname).getId();
    }
    public Clan findClanByWholeName(String clanwholename) {
        return personRepository.findClanByWholeName(clanwholename);
    }








}
