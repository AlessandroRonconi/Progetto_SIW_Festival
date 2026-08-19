package it.uniroma3.siw.siw_festival.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.siw_festival.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
