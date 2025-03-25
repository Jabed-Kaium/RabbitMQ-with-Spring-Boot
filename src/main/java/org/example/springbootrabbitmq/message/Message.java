package org.example.springbootrabbitmq.message;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private String id;
    private String content;
}
