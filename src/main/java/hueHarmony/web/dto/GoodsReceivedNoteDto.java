package hueHarmony.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsReceivedNoteDto {

    private long grnId;
    private long purchaseOrderId;
    private List<GoodsReceivedProductDto> receivedProducts;
    private Date receivedDate;
    private String remarks;
    private boolean isComplete;
}
