package api.dto.user;


import application.port.in.UserContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content-service")
@RequiredArgsConstructor
public class UserContentController {

    private final UserContentService userContentService;
}