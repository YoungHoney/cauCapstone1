package cap.backend.back.service;


import cap.backend.back.domain.Krpedia;
import cap.backend.back.repository.KrPediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RealService {

    private final KrPediaRepository krpediarepository;






}
