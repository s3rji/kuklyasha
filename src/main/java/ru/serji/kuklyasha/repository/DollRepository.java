package ru.serji.kuklyasha.repository;

import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.model.*;

@Transactional(readOnly = true)
public interface DollRepository extends BaseRepository<Doll> {

}
