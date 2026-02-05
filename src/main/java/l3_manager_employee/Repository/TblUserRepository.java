package l3_manager_employee.Repository;

import l3_manager_employee.Enity.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TblUserRepository extends JpaRepository<TblUser, Integer> {

    Optional<TblUser> findByUsernameAndStatus(String username, String status);
}