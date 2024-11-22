package application.service.user;

import application.port.in.UserContentService;
import application.port.out.UserContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserContentServiceImpl implements UserContentService {

    private final UserContentRepository userContentRepository;
}
