package hueHarmony.web.repository;

import hueHarmony.web.model.GoodsReceivedNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GRNRepository extends JpaRepository<GoodsReceivedNote, Long> {
    GoodsReceivedNote findGoodsReceivedNoteByGrnId(Long grnId);
}
