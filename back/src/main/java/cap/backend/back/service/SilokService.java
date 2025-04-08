package cap.backend.back.service;

import cap.backend.back.domain.Person;
import cap.backend.back.domain.Silok;
import cap.backend.back.domain.dto.SilokDocument;
import cap.backend.back.repository.PersonRepository;
import cap.backend.back.repository.SilokRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SilokService {

    private final SilokRepository silokRepository;
    private final PersonRepository personRepository;

    public void saveAll(Person person, List<SilokDocument> documents) {
        for (SilokDocument doc : documents) {
            Silok silok = new Silok();
            silok.setP_id(person.getId());
            silok.setEventyear(Integer.parseInt(doc.getPublicationYear()));
            silok.setContents(doc.getContent());
            silokRepository.save(silok);
        }
    }
}
