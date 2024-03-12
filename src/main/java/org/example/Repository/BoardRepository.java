package org.example.Repository;
import org.example.Entity.Board;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {


}

