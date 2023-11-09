package cap.backend.back.service;

import cap.backend.back.api.AzureApi;
import cap.backend.back.api.OpenAiApi;
import cap.backend.back.domain.dto.MessageDto;
import io.netty.handler.codec.MessageAggregationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final AzureApi azureApi;
    private final OpenAiApi openAiApi;

    public String getReplyFromAzure(MessageDto messageDto,String ancestorName) {

        return azureApi.getReply(messageDto,ancestorName);


    }

    public String getReplyFromOpenai(MessageDto messageDto) {

        return openAiApi.getReply(messageDto);


    }


}
