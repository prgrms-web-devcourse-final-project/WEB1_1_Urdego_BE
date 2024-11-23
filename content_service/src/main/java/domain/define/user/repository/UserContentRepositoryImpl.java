package infra.repository.user;

import application.port.out.UserContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserContentRepositoryImpl implements UserContentRepository {

    private final UserContentJPARepository userContentJPARepository;
}
