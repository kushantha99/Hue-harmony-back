package hueHarmony.web.repository;

import hueHarmony.web.model.GoodsReceivedNote;
import hueHarmony.web.model.GoodsReceivedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GRNProductRepository extends JpaRepository<GoodsReceivedProduct, Long> {
}
