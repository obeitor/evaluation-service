package com.evaluation.service.repository;

import com.evaluation.service.models.ChainBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChainBlockRepo extends JpaRepository<ChainBlock, Long> {
    @Query("SELECT c.currentHash FROM ChainBlock c ORDER BY c.id")
    List<String> getChain();

    Optional<ChainBlock> findFirstByOrderByIdDesc();
}
