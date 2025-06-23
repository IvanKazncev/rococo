package rococo.backends.users.controller;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import rococo.backends.users.dto.UserJson;
import rococo.backends.users.entity.UserEntity;
import rococo.backends.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/internal/users")
public class UserController {

  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public UserEntity allUsers(@RequestParam String username) throws ChangeSetPersister.NotFoundException {
    return userService.getRequiredUser(username);
  }

  @PatchMapping("/update")
  public UserJson updateUserInfo(@RequestBody UserJson user) {
    return userService.update(user);
  }

}
