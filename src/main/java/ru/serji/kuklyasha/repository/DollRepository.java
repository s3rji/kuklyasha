package ru.serji.kuklyasha.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.model.*;

import static ru.serji.kuklyasha.util.ValidationUtil.*;

@Transactional(readOnly = true)
public interface DollRepository extends JpaRepository<Doll, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Doll d WHERE d.id = :id")
    int delete(@Param("id") Integer id);

    @Transactional
    default void deleteExisted(int id) {
        checkModification(delete(id), id);
    }
}
