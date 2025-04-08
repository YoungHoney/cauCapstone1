package cap.backend.back.service;

import cap.backend.back.api.AiApi;
import cap.backend.back.domain.Krpedia;
import cap.backend.back.domain.dto.SilokDocument;
import cap.backend.back.domain.govrank.Govmatch;
import cap.backend.back.domain.govrank.Oldgov;
import cap.backend.back.domain.gptresults.Govsequence;
import cap.backend.back.domain.gptresults.Lifesummary;
import cap.backend.back.domain.gptresults.Mbti;
import cap.backend.back.domain.gptresults.Privatehistory;
import cap.backend.back.repository.GovRepository;
import cap.backend.back.repository.GptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GptResultService {

    private final GptRepository gptRepository;
    private final GovRepository govRepository;
    private final AiApi aiApi;

    public void generateGovsequence(Krpedia krpedia) {
        String input = krpedia.getDefinition() + krpedia.getDefinition() + krpedia.getMaintext();
        String response = aiApi.getGovsequence(input);
        String[] govParts = response.split(",");

        for (int i = 0; i < Math.min(govParts.length, 5); i++) {
            String[] splitPart = govParts[i].split(":");
            String govName = splitPart.length > 1 ? splitPart[1].trim() : "없음";

            Oldgov oldgov = govRepository.findOldgov(govName);
            if (oldgov == null) {
                oldgov = new Oldgov();
                oldgov.setName(govName + "(현대미상)");
                oldgov.setIswarrior(false);
                oldgov.setRank("종9품");
                govRepository.save(oldgov);

                Govmatch match = new Govmatch();
                match.setOldgov(oldgov);
                match.setModerngov(govRepository.findModerngov("현대미상"));
                govRepository.save(match);
            }

            Govsequence govsequence = new Govsequence();
            govsequence.setKrpedia(krpedia);
            govsequence.setOldgov(oldgov);
            govsequence.setSequnce_num(i + 1);
            gptRepository.save(govsequence);
        }
    }

    public void generateLifesummary(Krpedia krpedia, List<SilokDocument> siloks) {
        String input = krpedia.getDefinition() + krpedia.getDescription() + krpedia.getMaintext()
                + siloks.stream().map(SilokDocument::getContent).reduce("", String::concat);
        String content = aiApi.getLifesummary(input, krpedia.getName());

        Lifesummary summary = new Lifesummary();
        summary.setKrpedia(krpedia);
        summary.setContents(content);
        gptRepository.save(summary);
    }

    public void generateMbti(Krpedia krpedia, List<SilokDocument> siloks) {
        String input = krpedia.getDefinition() + krpedia.getDescription() + krpedia.getMaintext()
                + siloks.stream().map(SilokDocument::getContent).reduce("", String::concat);
        String response = aiApi.getMBTI(input, krpedia.getName());

        String[] parts = response.split("]");
        String mbtiCode = parts[0].substring(parts[0].length() - 4);
        String content = parts[1];

        Mbti mbti = new Mbti();
        mbti.setKrpedia(krpedia);
        mbti.setMbti(mbtiCode);
        mbti.setContents(content);
        gptRepository.save(mbti);
    }

    public void generatePrivateHistory(Krpedia krpedia, List<SilokDocument> siloks) {
        String input = krpedia.getDefinition() + krpedia.getDescription() + krpedia.getMaintext()
                + siloks.stream().map(SilokDocument::getContent).reduce("", String::concat);
        String response = aiApi.getPrivateHistory(input, krpedia.getName());

        String[] events = response.split("\\$");
        for (int i = 0; i < Math.min(events.length, 6); i++) {
            String[] parts = events[i].split(":");
            if (parts.length > 1 && parts[0].matches("\\d{4}")) {
                Privatehistory ph = new Privatehistory();
                ph.setKrpedia(krpedia);
                ph.setEventyear(Integer.parseInt(parts[0].trim()));
                ph.setContents(parts[1].trim());
                gptRepository.save(ph);
            }
        }
    }
}
