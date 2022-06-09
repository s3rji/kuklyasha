package ru.serji.kuklyasha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.serji.kuklyasha.model.Doll;

import static ru.serji.kuklyasha.util.ValidationUtil.checkModification;

@Transactional(readOnly = true)
public interface DollRepository extends JpaRepository<Doll, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Doll d WHERE d.id = :id")
    int delete(@Param("id") Integer id);

    default void deleteExisted(int id) {
        checkModification(delete(id), id);
    }
}
