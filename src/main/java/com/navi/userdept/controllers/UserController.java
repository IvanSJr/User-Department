package com.navi.userdept.controllers;

import com.navi.userdept.entities.User;
import com.navi.userdept.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createNewUser(@RequestBody User user){
        URI location = null;
        User result = userRepository.save(user);
        try {
            location = new URI("http://localhost:8080/users/"+user.getId());
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> findAll(){
        List<User> results = userRepository.findAll();
        return ResponseEntity.ok().body(results);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = userRepository.findById(id).get();
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        URI location = null;
        try {
            location = new URI("http://localhost:8080/users/"+id);
            userRepository.deleteById(id);
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        return ResponseEntity.noContent().location(location).build();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setDepartment(user.getDepartment());
        final User userUpdated = userRepository.save(userToUpdate);
        return ResponseEntity.ok().body(userUpdated);
    }
}
