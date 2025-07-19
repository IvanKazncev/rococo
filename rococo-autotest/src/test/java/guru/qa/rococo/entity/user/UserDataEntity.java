package guru.qa.rococo.entity.user;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class UserDataEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private UUID id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column()
  private String firstname;

  @Column()
  private String lastname;

  @Column()
  private String avatar;

}