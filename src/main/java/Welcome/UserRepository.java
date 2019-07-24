package Welcome;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select s from UserEntity s where s.username=?1")
    public UserEntity findByUsername(String username);

    @Query("select s from UserEntity s where s.username=?1 and s.password=?2")
    public UserEntity findByUsernameAndPassword(String username, String password);
}
