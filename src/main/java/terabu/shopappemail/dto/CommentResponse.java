package terabu.shopappemail.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
@JsonDeserialize
@Data
public class CommentResponse {
    private String email;
    private String comments;
    private String name;
    private String id;
}
