package cap.backend.back.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
public class ChatHistoriesDTO {
    private ChatListsDTO[] buffer;
    private int size;
    private int front;
    private int rear;

    public ChatHistoriesDTO(int capacity) {
        buffer = new ChatListsDTO[capacity];
        for (int i = 0; i < capacity; i++) {
            buffer[i] = new ChatListsDTO();
        }
        size = 0;
        front = 0;
        rear = 0;
    }

    public void add(String userChat, String gptChat) {
        if (size == buffer.length) {
            removeOldest();
        }

        buffer[rear].setUserChat(userChat);
        buffer[rear].setGPTChat(gptChat);

        rear = (rear + 1) % (buffer.length);
        size++;
    }

    private void removeOldest() {
        buffer[front].clear();
        front = (front + 1) % buffer.length;
        size--;
    }
    public String historiesToString(){
        if(size != 0) {
            StringBuilder concatenated = new StringBuilder();
            int head = front;
            do{
                String temp = buffer[head].chatListsToString();
                if (temp != null) {
                    concatenated.append(temp);
                }
                head = (head + 1) % buffer.length;
            }while (head != rear);

            return concatenated.toString();
        }
        else return "";
    }
    public ChatListsDTO get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        int actualIndex = (front + index) % buffer.length;
        return buffer[actualIndex];
    }

    public void clearHistory(){
        for(ChatListsDTO buff: buffer){
            buff.clear();
        }
        size = 0;
        front = 0;
        rear = 0;
    }
}
