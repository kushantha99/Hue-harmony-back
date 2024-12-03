package hueHarmony.web.dto;

import hueHarmony.web.annotation.validations.DataExistingValidation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerDto {

    public interface onCreate{}
    public interface onUpdate{}
    public interface onDelete{}

//    @NotNull
//    @DataExistingValidation(service = null, method = "")
//    private int bannerId;

    @NotNull

    private String imageUrl;
}
