package com.Ninjva.Coding_Ninja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {


    // Creating Object
    @Autowired
    EmployeeRepository employeeRepository;

    // Add Employee Details
    @PostMapping("/employee")
    public String createNewEmployee(@RequestBody Employee employee){
        employeeRepository.save(employee);
        return "Employee Added Successfully";
    }



    // List of all Employee
    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAllEmployeeDetails(){
        List<Employee> empList = new ArrayList<>();
        employeeRepository.findAll().forEach(empList :: add);
        return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);
    }



    // Search Employee By Id
    @GetMapping("/employee/{Id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer Id){
       Optional<Employee>emp = employeeRepository.findById(Id);
       if(emp.isPresent()){
           return new ResponseEntity<Employee>(emp.get(),HttpStatus.FOUND);
       }else {
           return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
       }
    }

    // Update Employee details

    @PutMapping("/employee/{Id}")
    public String UpdateEmployeeById(@PathVariable Integer Id, @RequestBody Employee employee){
        Optional<Employee> emp = employeeRepository.findById(Id);
        if(emp.isPresent()){
            Employee existEmp = emp.get();
            existEmp.setName(employee.getName());
            existEmp.setAge(employee.getAge());
            existEmp.setSalary(employee.getSalary());
            existEmp.setCity(employee.getCity());
            employeeRepository.save(existEmp);
            return "Employee datails update given Id: "+Id+" .";
        }
        else
            return "Employee Does not Exist given Id: "+Id+" .";
    }

    // Delete Employee By Id
    public String deleteEmployeeById(@PathVariable Integer Id){
        employeeRepository.deleteById(Id);
        return "Employee Deleted SuccessFully Id: "+Id+".";
    }


    // Delete All Employee
    public String deleteAllEmployee(){
        employeeRepository.deleteAll();
        return "All Employee Details Deleted Successfully";
    }

}
