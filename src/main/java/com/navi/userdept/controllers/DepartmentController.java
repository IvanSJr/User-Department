package com.navi.userdept.controllers;

import com.navi.userdept.entities.Department;
import com.navi.userdept.entities.User;
import com.navi.userdept.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Department> createNewDepartment(@RequestBody Department department){
        URI location = null;
        Department result = departmentRepository.save(department);
        try {
            location = new URI("http://localhost:8080/departments/"+department.getId());
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Department>> findAllDepartments(){
        List<Department> results = departmentRepository.findAll();
        return ResponseEntity.ok().body(results);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Department> findById(@PathVariable Long id){
        Department department = departmentRepository.findById(id).get();
        return ResponseEntity.ok().body(department);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department body){
        Department department = departmentRepository.findById(id).get();
        department.setName(body.getName());
        final Department updatedDepartment = departmentRepository.save(department);
        return ResponseEntity.ok().body(updatedDepartment);
    }

}
