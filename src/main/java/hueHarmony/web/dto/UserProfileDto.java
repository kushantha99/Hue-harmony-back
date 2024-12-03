package hueHarmony.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private int userId = -1;
    private String avatar;
    private String username;

    public UserProfileDto(int id, String image){
        this.userId = id;
        this.avatar = image;
    }
}
